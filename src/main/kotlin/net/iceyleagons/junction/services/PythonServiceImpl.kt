package net.iceyleagons.junction.services

import net.iceyleagons.junction.api.PythonService
import java.io.BufferedReader
import java.nio.file.Path
import kotlin.io.path.absolutePathString

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
// Just in case we actually need ml
class PythonServiceImpl : PythonService {

    override fun invokePythonScript(path: Path, vararg arguments: String) {
        val pb = ProcessBuilder("python3", path.absolutePathString(), *arguments).redirectErrorStream(true)
        val process = pb.start()
        val output = readProcessOutput(process)

        val exitCode = process.waitFor()
    }

    private fun readProcessOutput(process: Process): List<String> {
        val result: MutableList<String> = ArrayList(20)
        BufferedReader(process.inputStream.reader()).use {
            result.add(it.readLine())
        }
        return result
    }
}