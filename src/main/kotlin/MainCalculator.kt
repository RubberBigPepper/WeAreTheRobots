import kotlin.math.min
import kotlin.random.Random

class MainCalculator(private val maxTips: Int, private val robotCost: Int, private val map: Map) {
    private val rovers = mutableListOf<Rover>()

    private val orders = mutableListOf<Order>()

    //раскидаем равномерно роботов на карте
    fun generateRovers(){
        val count = 1
        for(n in 0 until count){
            val X = Random.nextInt(map.N)
            val Y = Random.nextInt(map.N)
            if (map.canGo(X, Y))
                rovers.add(Rover(MapCoord(X,Y)))
        }
    }

    //одна итерация - то есть 60 шагов каждого из роботов
    fun run(){
        for(steps in 0 until 60) {
            for (rover in rovers) {
                if (!rover.makeStep()) {//маршрут у робота закончился - нужно сгенерировать ему новую цель
                    generateNewRouteForRobot(rover)
                    rover.makeStep()
                }
            }
        }
    }

    private fun getNearestOrder(rover: Rover): Order?{
        var res: Order? = null
        var minDistance = Int.MAX_VALUE
        for(order in orders) {
            val route = Route.calculateRoute(rover.currentCoord, order.to, map) ?: continue
            val leng = route.length
            if (leng< minDistance) {
                res = order
                minDistance = leng
            }
        }
        return res
    }

    private fun generateNewRouteForRobot(rover: Rover){
        var resOrder: Order? = null
        var resRoute: Route? = null
        var minDistance = Int.MAX_VALUE
        for(order in orders) {
            val route = Route.calculateRoute(rover.currentCoord, order.to, map) ?: continue
            val leng = route.length
            if (leng< minDistance) {
                resOrder = order
                resRoute = route
                minDistance = leng
            }
        }
        if (resOrder!=null && resRoute !=null) {//есть новые маршруты для робота
            rover.currentOrder = resOrder
            rover.route = resRoute

            orders.remove(resOrder)
        }
    }
}