package rnd.grails3.httpinvoker.producer

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification


@TestFor(BookService)
@Mock([Book])
class BookServiceSpec extends Specification {

    def setup() {
        (1..5000).each {
            def book = Book.build()
            book.save()
        }
    }

    def cleanup() {
    }

    void "test remote db access time"() {
        given:
        def allBooks = service.getAllBooks()

        expect:
        allBooks != null
    }
}
