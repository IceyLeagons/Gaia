package net.iceyleagons.junction.detectors

import java.math.BigDecimal
import java.util.*

data class UserInput(
    val ip: String,
    val knownLocations: Optional<List<String>>,
    val previousIPs: Optional<TreeMap<Long, String>>, // key --> time of login, string --> ip
    val carriers: Optional<List<String>>,
    val shippingAddress: Optional<String>,
    val billingAddress: Optional<String>,
    val gpsLatitude: Optional<Double>,
    val gpsLongitude: Optional<Double>
)