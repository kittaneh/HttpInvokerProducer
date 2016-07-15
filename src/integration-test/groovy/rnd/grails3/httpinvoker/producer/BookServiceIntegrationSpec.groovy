package rnd.grails3.httpinvoker.producer


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class BookServiceIntegrationSpec extends Specification {

    BookService bookService

    def setup() {
    }

    def cleanup() {
    }


    @Unroll
    void "test remote db access time for JSON #a"() {
        given:
        def allBooks = bookService.getAllBooks()

        expect:
        allBooks != null

        where:
        a << [1,2,3]
    }


    @Unroll
    void "test remote db access time for DTO #a"() {
        given:
        def allBooks = bookService.getAllBooksDTO()

        expect:
        allBooks != null

        where:
        a << [1,2,3]
    }
}
