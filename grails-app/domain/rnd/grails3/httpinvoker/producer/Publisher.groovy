package rnd.grails3.httpinvoker.producer

/**
 * <h1>Publisher Domain Class</h1>
 *  Nested domain class to test exposing JSON data
 *  through HTTPInvoker or REST
 * @author  yahya
 * @version 1.0stop
 */

class Publisher {

    String name
    Country country


    static constraints = {
        name(nullable: true)
        country(nullable: true)
    }
}
