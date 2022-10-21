package net.iceyleagons.junction.api

import java.nio.file.Path

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface PythonService {

    fun invokePythonScript(path: Path, vararg arguments: String)

}