package rnd.grails3.httpinvoker.producer

/**
* <h1>Book Domain Class</h1>
*  Nested domain class to test exposing JSON data
*  through HTTPInvoker or REST
* @author  yahya
* @version 1.0
*/
class Book {

    String title
    String ISBN
    String createdBy

    Boolean hardCover
    Long pagesNumber
    Float weight

    Date dateCreated
    Date lastUpdated

    Category category
    Publisher publisher


    static belongsTo = [author: Author]

    static constraints = {
        title(nullable: false)
        ISBN(nullable: false)

        author(nullable: false)
        publisher(nullable: false)
        category(nullable: false)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        weight(nullable: true)
        pagesNumber(nullable: true)
        hardCover(nullable: true)
        createdBy(nullable: true)
    }

    enum Category {
        IT,
        ECONOMICS,
        NOVEL
    }

}
