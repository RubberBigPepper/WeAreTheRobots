import kotlin.math.abs

//карта, в которой будут двигаться роверы
class Map(val N: Int) {
    private val cells = Array(N * N) {
        true
    }

    private fun flatIndex(col: Int, row: Int): Int = row * N + col

    private fun flatIndex(coord: MapCoord): Int = flatIndex(coord.X, coord.Y)

    fun canGo(col: Int, row: Int): Boolean {
        if (col in 0 until N && row in 0 until N)
            return cells[flatIndex(col, row)]
        return false
    }

    fun canGo(coord: MapCoord): Boolean = canGo(coord.X, coord.Y)

    //метрика пространства
    fun distance(from: MapCoord, to: MapCoord): Int {
        return abs(from.X - to.X) + abs(from.Y - to.Y)
    }

    fun setWall(col: Int, row: Int){
        cells[flatIndex(col, row)] = false
    }
}