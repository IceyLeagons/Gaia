package net.iceyleagons.gaia.api.wifi

import net.iceyleagons.gaia.api.wifi.responses.WigleResponse

/**
 * Searches for publicly - and freely - available wifi networks.
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
interface WigleService {

    /**
     * Searches for all the wifi networks found from 2021- onwards in the range of [range] from the center provided by the coordinates [latitude] [longitude].
     *
     * @param latitude the latitude of the center of this search.
     * @param longitude the longitude of the center of this search.
     * @param range the range in which we want to search for networks in.
     * @return all the networks that are inside the provided search range.
     */
    fun findPublicWifisAround(
        latitude: Double,
        longitude: Double,
        range: Double
    ): Array<WigleResponse.WigleNetwork>

}