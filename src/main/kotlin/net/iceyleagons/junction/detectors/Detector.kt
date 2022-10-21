package net.iceyleagons.junction.detectors

import org.json.JSONObject
import org.springframework.beans.factory.BeanFactory

abstract class Detector(val name: String, val requiresAccurateData: Boolean) {

    /**
     * @param userInput the input passed via the endpoint
     * @param context is the bean context (used for getting necessary services)
     * @param json the response object (can be written to)
     *
     * @return a score from 0-100, where 0 is not at all fraudulent, and 100 is the absolute definition of fraud
     */
    abstract fun getScore(userInput: UserInput, context: BeanFactory, json: JSONObject): Int

}