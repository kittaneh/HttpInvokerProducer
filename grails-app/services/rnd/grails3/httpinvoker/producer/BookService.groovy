package rnd.grails3.httpinvoker.producer

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.time.TimeCategory
import groovy.time.TimeDuration
import org.grails.web.json.JSONElement
import rnd.grails3.httpinvoker.producer.dto.BookDTO
import rnd.grails3.httpinvoker.remoting.IBook

/**
* <h1>Book Service</h1>
* Service to test exposing JSON data
* through HTTPInvoker
* @author  yahya
* @version 1.0
*/

class BookService implements IBook {

    // let the httpinvoker produce this service to the world!
    static expose = ['httpinvoker']

     CommonService commonService

    /**
     * Read Book instance by Id
     * @param bookId
     * @return pretty JSON string representation of the book object
     */
    @Transactional
    @Override
    public String findBookById(Long bookId){

        String theBook

        //Read the book object instead of using get
        Book book = Book.read(bookId)


        //pretty string for JSON representation
        if(book){
            // this will go deep converting the objects
            JSON.use('deep'){
                theBook = (book as JSON).toString(true)
            }

        }

        return theBook
    }

    /**
     * Get all the book instances filled to load test data
     * @return pretty JSON string representation of the all books objects
     */
    @Override
    public String getAllBooks(){

        List<Book> bookList = getBooks("HTTPInvoker with JSON")
        String bookListString

        if(bookList){
          //  def timeStart = new Date()
            bookListString = (bookList as JSON).toString(true)
           // def timeStop = new Date()
           // TimeDuration duration = TimeCategory.minus(timeStop, timeStart)
           // println " producer:httpInvoker marshalling to JSON " + duration.toMilliseconds()
        }

        return bookListString

    }


    /**
     * Get all the book instances filled to load test data
     * @return pretty JSON string representation of the all books objects
     */
    @Override
    public List<BookDTO> getAllBooksDTO(){

        List<Book> bookList = getBooks("HTTPInvoker with DTO")
        List<BookDTO> bookListDTO

        if(bookList){
            bookListDTO = bookList.toDTO(BookDTO)
        }

        return bookListDTO

    }

    /**
     * Saves new Book instance or update an existing one
     * @param bookJSON : a string representation of the book
     * @return pretty JSON string representation of the book persisted object , or an error JSON object
     */
    @Transactional
    @Override
    public String save(String bookJSON){

        // parse the string to JSON
        JSONElement book = JSON.parse(bookJSON)
        Book bookInstance

        if(book.id){ // The id is referenced directly in the JSON object
            bookInstance = Book.get(book.id)
            bookInstance.properties = book // bind data
        }else{
            bookInstance = new Book(book) // bind data
        }

        bookInstance.save()


        //custom JSON with errors  in the same result
         String result = commonService.getDomainClassCustomJSON("create",bookInstance,null,null)
         return result

        /*
        // uncomment this code to send separated results (one result for the domain ,
        //   and another result for errors)

        //return errors if any
        if(bookInstance.hasErrors()){
            return (commonService.getDomainClassValidationErrors(bookInstance) as JSON).toString(true)
        }

        //pretty string representation
        return (bookInstance as JSON).toString(true)
        */
    }

    /** encapsulates db access for both REST and HttpInvoker
     * @param protocol : a description string to be printed
     * @return Book list
     */
    @Transactional
    public List<Book> getBooks(String protocol){

        def timeStart = new Date()
        List bookList = Book.list()
        def timeStop = new Date()
        TimeDuration duration = TimeCategory.minus(timeStop, timeStart)
        println "producer:$protocol db time: " + duration.toMilliseconds()

        return bookList
    }


}
