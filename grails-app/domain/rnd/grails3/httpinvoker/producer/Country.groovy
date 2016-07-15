package rnd.grails3.httpinvoker.producer

/**
 * <h1>Country Domain Class</h1>
 *  Nested domain class to test exposing JSON data
 *  through HTTPInvoker or REST
 * @author  yahya
 * @version 1.0
 */
class Country {

    String code
    String name

    static constraints = {
    }
}
