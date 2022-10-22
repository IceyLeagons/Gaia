package net.iceyleagons.junction.detectors

import org.springframework.beans.factory.BeanFactory

interface Detector {

    /**
     * The name of this detector.
     */
    val name: String

    /**
     * Whether this detector requires precise location data.
     */
    val requiresGpsData: Boolean

    val maxScore: Int

    /**
     * @param userInput the input passed via the endpoint
     * @param context is the bean context (used for getting necessary services)
     *
     * @return a score from 0-100, where 0 is not at all fraudulent, and 100 is the absolute definition of fraud
     */
    fun getScore(userInput: UserInput, context: BeanFactory): Rule

}