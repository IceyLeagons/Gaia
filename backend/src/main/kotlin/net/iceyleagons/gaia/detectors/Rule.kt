package net.iceyleagons.gaia.detectors

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.iceyleagons.gaia.services.DetectionServiceImpl

/**
 * Serializable data class containing information that'll be later serialized into the output of [DetectionServiceImpl.checkForFraud].
 *
 * @author Gabe
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
        /**
         * Default rule.
         *
         * Lazily initiated.
         */
        val EMPTY_RULE by lazy {
            Rule(DetectionServiceImpl.detectors[0], "NO ANOMALY", '0', 0.0)
        }
    }

    /**
     * Internal (de)serializer for [Detector]s.
     */
    @Serializer(forClass = Detector::class)
    internal object DetectorSerializer : KSerializer<Detector> {
        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Detector", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): Detector {
            return DetectionServiceImpl.detectors.find { it.name == decoder.decodeString() }!!
        }

        override fun serialize(encoder: Encoder, value: Detector) {
            encoder.encodeString(value.name)
        }
    }
}
