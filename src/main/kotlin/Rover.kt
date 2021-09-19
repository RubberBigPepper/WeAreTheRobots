enum class Movement {
    up, down, left, right, stand, take, pickup
}

enum class Action {
    nothing, take, pickup
}

//класс описания ровера
class Rover(val initialPoint: MapCoord, private val maxSteps: Int = 60) {
    private val steps = mutableListOf<Movement>()//как ехал ровер

    //маршрут движения робота
    private var route: Route? = null

    //что делать роверу после прибытия в конечную точку маршрута
    private var endRouteAction: Action = Action.nothing

    var currentRouteStep = 0

    val stepCount: Int
        get() = steps.size

    fun makeStep(): Boolean {
        if (steps.size == maxSteps)
            return false
        if (route == null) {//нет маршрута - ходить некуда, стоим
            steps.add(Movement.stand)
            return false
        }
        else {
            route?.let {
                if (it.nextMovement != null) {
                    steps.add(it.nextMovement!!)
                    it.step++
                    currentRouteStep++
                }
                else{
                    when(endRouteAction){
                        Action.take-> steps.add(Movement.take)
                        Action.pickup-> steps.add(Movement.pickup)
                        else -> return false
                    }
                }
            }
            return true
        }
    }
}