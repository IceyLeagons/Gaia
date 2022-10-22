package net.iceyleagons.gaia.detectors

import java.util.*

/**
 * Data class containing information provided for the user.
 *
 * @author TOTHTOMI, Gabe
 * @version 1.0.0
 * @since Oct. 21, 2022
 */
data class UserInput(
    /**
     * The IP address of the user.
     */
    val ip: String,

    /**
     * All the known locations that this user has been to.
     *
     * Currently unused.
     */
    @Deprecated("Currently unused.")
    val knownLocations: Optional<List<String>>,

    /**
     * All the previous ips this user has been associated with.
     *
     * Key is the time of login, value is the ip.
     */
    val previousIPs: Optional<TreeMap<Long, String>>,

    /**
     * All the carriers this user has been using. (Of course, only those that've been recorded)
     *
     * Currently unused.
     */
    @Deprecated("Currently unused.")
    val carriers: Optional<List<String>>,

    /**
     * The shipping address of this user.
     */
    val shippingAddress: Optional<String>,

    /**
     * The billing address of this user.
     */
    val billingAddress: Optional<String>,

    /**
     * The **EXACT** latitude of this user.
     */
    val gpsLatitude: Optional<Double>,

    /**
     * The **EXACT** longitude of this user.
     */
    val gpsLongitude: Optional<Double>
)