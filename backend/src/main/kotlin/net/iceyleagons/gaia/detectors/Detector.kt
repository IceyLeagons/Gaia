package net.iceyleagons.gaia.detectors

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

    /**
     * The maximum score this detector can give.
     *
     * Used for weighting.
     */
    val maxScore: Int

    /**
     * @param userInput the input passed via the endpoint
     * @param context is the bean context (used for getting necessary services)
     *
     * @return a score from 0-[maxScore], where 0 is not at all fraudulent, and [maxScore] is the absolute definition of fraud
     */
    fun getScore(userInput: UserInput, context: BeanFactory): Rule

}