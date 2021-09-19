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
    var route: Route? = null

    //что делать роверу после прибытия в конечную точку маршрута
    private var endRouteAction: Action = Action.nothing

    var currentRouteStep = 0

    var currentCoord: MapCoord = initialPoint

    val stepCount: Int
        get() = steps.size

    var currentOrder: Order? = null

    fun makeStep(): Boolean {
        if (steps.size == maxSteps)
            return false
        if (route == null) {//нет маршрута - ходить некуда, стоим
            steps.add(Movement.stand)
            return false
        } else {
            route?.let {
                if (it.nextMovement != null) {
                    steps.add(it.nextMovement!!)
                    currentCoord = when (it.nextMovement) {
                        Movement.up -> MapCoord(currentCoord.X, currentCoord.Y - 1)
                        Movement.down -> MapCoord(currentCoord.X, currentCoord.Y + 1)
                        Movement.left -> MapCoord(currentCoord.X - 1, currentCoord.Y)
                        Movement.right -> MapCoord(currentCoord.X + 1, currentCoord.Y)
                        else -> currentCoord
                    }
                    it.step++
                    currentRouteStep++
                } else {
                    when (endRouteAction) {
                        Action.take -> steps.add(Movement.take)
                        Action.pickup -> steps.add(Movement.pickup)
                        else -> return false
                    }
                    route = null
                }
            }
            return true
        }
    }
}