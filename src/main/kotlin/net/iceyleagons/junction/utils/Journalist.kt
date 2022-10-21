package net.iceyleagons.junction.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface Journalist {

    val logger: Logger
        get() = LoggerFactory.getLogger(this.javaClass)

}