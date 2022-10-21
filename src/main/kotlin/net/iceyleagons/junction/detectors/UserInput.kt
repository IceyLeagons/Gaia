package net.iceyleagons.junction.detectors

import java.math.BigDecimal
import java.util.*

data class UserInput(
    val ip: String,
    val shippingAddress: Optional<String>,
    val billingAddress: Optional<String>,
    val gpsLat: Optional<BigDecimal>,
    val gpsLon: Optional<BigDecimal>
)