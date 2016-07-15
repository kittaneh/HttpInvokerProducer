package rnd.grails3.httpinvoker.producer

import grails.rest.RestfulController

/**
 * <h1>Rest Book Controller</h1>
 * @author yahya
 * @version 1.0
 */
class BookController extends RestfulController {
    static responseFormats = ['json', 'xml']


    BookService bookService
    def gsonBuilder

    BookController() {
        super(Book)
    }


    /**
       Overridden to deeply return the object traversal
        with all its children
     */
    @Override
    protected Book queryForResource(Serializable id) {
        Book.get(id)
    }


    /**
       Overridden to deeply return the object traversal
        with all its children
     */
    @Override
    def show() {
       // JSON.use("deep"){
            respond queryForResource(params.id)
       // }
    }


    /**
        Overridden enable search
        for example : http://localhost:8080/httpInvokerProducer/books/?q=Grails
        with all its children
     */
    @Override
    def index() {
        /*
        respond uses content-negotiation to determine what content type to render
        http://grails.github.io/grails-doc/latest/ref/Controllers/respond.html
        */

        //uncomment the following if you want to use the search
        // def booksList = Book.where { title =~ "%${params.q}%" }.list()

        def booksList
        if(params.parser=="gson"){
            booksList = bookService.getBooks("REST with GSON")
            def gson = gsonBuilder.create()
            render contentType: 'application/json', text: gson.toJson(booksList)

        } else{
            booksList = bookService.getBooks("REST with JSON")
            respond booksList
        }

    }


}