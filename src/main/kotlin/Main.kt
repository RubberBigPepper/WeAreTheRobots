import java.io.*


fun readMap(input: BufferedReader, mapSize: Int): Map? {
    try {
        val map = Map(mapSize)
        var row = 0
        var inputLine: String
        while (input.readLine().also { inputLine = it } != null) {
            var col = 0
            for (symbol in inputLine) {
                if (symbol == '#')
                    map.setWall(col, row)
                col++
            }
            row++
        }
        return map
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

fun readInputFirst(stream: InputStreamReader): MainCalculator? {
    try {
        BufferedReader(stream).use { input ->
            var inputLine: String
            if (input.readLine().also { inputLine = it } != null) {//прочитали размер города и суммы
                val fields = inputLine.split(" ")
                if (fields.size == 3) {
                    val mapSize = fields[0].toInt()
                    val maxTips = fields[1].toInt()
                    val robotCost = fields[2].toInt()
                    val world = readMap(input, mapSize)
                    if (world != null)
                        return MainCalculator(maxTips, robotCost, world)
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}


fun main(args: Array<String>) {

}