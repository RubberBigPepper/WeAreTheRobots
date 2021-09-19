import java.util.*

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

    val length = routePoint.size

    companion object {
        //для вычисления маршрута используем алгоритм A*
        fun calculateRoute(from: MapCoord, to: MapCoord, map: Map): Route? {
            //множество уже пройденных вершин
            val closed: MutableSet<MapCoord> = mutableSetOf()
            //множество частных решений
            val open: MutableList<MutableList<MapCoord>> = mutableListOf()
            open.add(mutableListOf(from))
            while (!open.isEmpty()) {
                //берем путь из коллекции путей
                val path = open.removeAt(0)
                //берем последнюю точку пути
                val end = path.last()
                //сравниваем - ее уже прошли? - пропускаем
                if (closed.contains(end))
                    continue
                //дошли до конца маршрута - выходим с результатом
                if (end == to) {
                    val result = Route(from, to)
                    result.routePoint.addAll(path)
                    return result
                }
                closed.add(end)
                //добавляем смежные вершины
                for (n in 0 until 4) {
                    val newPossibleMove = when (n) {
                        0 -> MapCoord(end.X, end.Y + 1)
                        1 -> MapCoord(end.X, end.Y - 1)
                        2 -> MapCoord(end.X + 1, end.Y)
                        3 -> MapCoord(end.X - 1, end.Y)
                        else -> break
                    }
                    if (map.canGo(newPossibleMove)) {
                        val newPath = mutableListOf<MapCoord>()
                        newPath.addAll(path)
                        newPath.add(newPossibleMove)
                        open.add(newPath)
                    }
                }
            }
            return null
        }
    }
}