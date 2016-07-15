package rnd.grails3.httpinvoker.producer

import grails.converters.JSON

/**
* <h1>Home Controller</h1>
* Testing domain classes CRUD
* @author  yahya
* @version 1.0
*/

class HomeController {

    BookService bookService

    def index() {
        render (Book.list()) as JSON
    }

    def allBooksDTO(){
        println bookService.getAllBooksDTO()
        return []
    }
}
