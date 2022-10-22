package net.iceyleagons.gaia

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
@Controller
class WebController {

    @RequestMapping("/")
    fun index(): String = "index"


}