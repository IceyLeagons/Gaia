package net.iceyleagons.junction.detectors

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.iceyleagons.junction.services.DetectionServiceImpl

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since Oct. 22, 2022
 */
@Serializable
data class Rule(
        @Serializable(with = DetectorSerializer::class) val detector: Detector,
        val anomaly: String,
        val operation: Char,
        val score: Double
    ) {

    companion object {
        val EMPTY_RULE by lazy {
            Rule(DetectionServiceImpl.detectors[0], "NO ANOMALY", '0', 0.0)
        }
    }

    @Serializer(forClass = Detector::class)
    object DetectorSerializer : KSerializer<Detector> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Detector", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): Detector {
            return DetectionServiceImpl.detectors.find { it.name == decoder.decodeString() }!!
        }

        override fun serialize(encoder: Encoder, value: Detector) {
            encoder.encodeString(value.name)
        }
    }
}
