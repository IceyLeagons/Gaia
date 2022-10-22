package net.iceyleagons.junction.detectors

import java.util.*

data class UserInput(
    val ip: String, // on website
    val knownLocations: Optional<List<String>>,
    val previousIPs: Optional<TreeMap<Long, String>>, // key --> time of login, string --> ip | on website
    val carriers: Optional<List<String>>,
    val shippingAddress: Optional<String>, // on website
    val billingAddress: Optional<String>, // on website
    val gpsLatitude: Optional<Double>, // on website
    val gpsLongitude: Optional<Double> // on website
)