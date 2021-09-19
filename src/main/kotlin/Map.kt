//карта, в которой будут двигаться роверы
class Map(val N: Int) {
    private val cells = Array(N * N) {
        true
    }

    private fun flatIndex(coord: MapCoord): Int = coord.Y * N + coord.X

    fun canGo(coord: MapCoord): Boolean {
        if (coord.X >= 0 && coord.X < N && coord.Y >= 0 && coord.Y < N)
            return cells[flatIndex(coord)]
        return false
    }
}