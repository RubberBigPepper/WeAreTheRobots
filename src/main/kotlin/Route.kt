class Route private constructor(val start: MapCoord, val end: MapCoord) {
    private val routePoint = mutableListOf<MapCoord>()

    //текущий шаг на маршруте
    var step: Int = 0

    val currentPoint: MapCoord
        get() = routePoint[step]

    val nextPoint: MapCoord?
        get() {
            if (step == routePoint.lastIndex)
                return null
            return routePoint[step + 1]
        }

    val nextMovement: Movement?
        get() {
            val next = nextPoint ?: return null
            val cur = currentPoint
            return when {
                next.Y == cur.Y && next.X > cur.X -> Movement.right
                next.Y == cur.Y && next.X < cur.X -> Movement.left
                next.X == cur.X && next.Y > cur.Y -> Movement.down
                next.X == cur.X && next.Y < cur.Y -> Movement.up
                else -> null
            }
        }

    companion object {
        //для вычисления маршрута используем алгоритм A*
        fun calculateRoute(start: MapCoord, end: MapCoord, map: Map): Route {
            
            return Route(start, end)
        }
    }
}