package net.iceyleagons.gaia.api.wifi.responses

import kotlinx.serialization.Serializable

/**
 * A serializable data class containing information about Wigle-provided networks.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
@Serializable
data class WigleResponse(
    /**
     * Whether the request was successful or not.
     */
    val success: Boolean,

    /**
     * The amount of results before the filtering.
     */
    val totalResults: Int,

    /**
     * The (starting from 1) index of the first element.
     */
    val first: Int,

    /**
     * The (yet again, starting from 1) index of the last element.
     */
    val last: Int,

    /**
     * The amount of results after filtering.
     */
    val resultCount: Int,

    /**
     * All the networks that are valid to the filtering rules.
     */
    val results: Array<WigleNetwork>
) {
    /**
     * A serializable data class containing information about a Wigle-provided network.
     *
     * @author TOTHTOMI, Gabe
     * @version 1.0.0
     * @since Oct. 21, 2022
     */
    @Serializable
    data class WigleNetwork(
        /**
         * The latitude that this network was discovered on.
         */
        val trilat: Float,
        /**
         * The longitude that this network was discovered on.
         */
        val trilong: Float,
        /**
         * The SSID with which this network was found.
         */
        val ssid: String,
        /**
         * The QoS number that was caught upon the discovering of this network.
         *
         * A number ranging from 0 to 7 (inclusive, 0..7)
         */
        val qos: Int,
        /**
         * The type of this network.
         *
         * Can be BT, Cell or wifi
         */
        val type: String,

        /**
         * If this network is possibly a freenet - public wifi that is accessible by anyone.
         *
         * Possible values include: 'Y', 'N', '?'
         */
        val freenet: String
    )
}