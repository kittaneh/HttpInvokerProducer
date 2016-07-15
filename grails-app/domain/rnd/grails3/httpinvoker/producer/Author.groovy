package rnd.grails3.httpinvoker.producer


/**
* <h1>Author Domain Class</h1>
*  Nested domain class to test exposing JSON data
*  through HTTPInvoker or REST
* @author  yahya
* @version 1.0
*/
class Author {

    String name

    static hasMany = [books:Book]

    static constraints = {
    }

    static mapping = {
        books lazy: true
    }

}
