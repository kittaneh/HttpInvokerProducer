import grails.converters.JSON
import rnd.grails3.httpinvoker.producer.Author
import rnd.grails3.httpinvoker.producer.Book

class BootStrap {


    /**
     *  fill test data to the Book and Author
     *  domain classes
     */
    private void fillData(){

        fillAuthors()

        new Book(title:'Grails In Action',author:Author.findByNameLike('%Yahya%')).save()
        new Book(title:'Groovy In Action',author:Author.findByNameLike('%Hamaydeh%')).save()

        // testing two books for the same author
        new Book(title:'Spring Boot In Action',author:Author.findByNameLike('%Isleem%')).save()
        new Book(title:'Java In Action',author:Author.findByNameLike('%Isleem%')).save()


    }


    /**
     *  fill test data to the Book full graph
     */
    private void fillMuchData(int rowsNum){

         // fillAuthors()

          (1..rowsNum).each{
              def book = Book.build()
              book.author = Author.findByNameLike('%Isleem%')
              book.save()
          }
    }

    /**
     *  fill test data to the Author
     *  domain class
     */
    private void fillAuthors(){

        List authors = ['Yahya Kittaneh', 'Mohammad Hamaydeh', 'Mohammad Isleem']

        authors.each{ String name ->
            Author author = new Author(name:name)
            author.save(flush:true)
        }
    }


    def init = { servletContext ->
      //  fillMuchData(5000)
        JSON.registerObjectMarshaller(Date) {
            return it?.format("yyyy-MM-dd'T'HH:mm:ss'Z'")
        }

        JSON.registerObjectMarshaller(Book.Category) { Book.Category cat ->
                    return cat.toString()
        }
    }
    def destroy = {
    }
}
