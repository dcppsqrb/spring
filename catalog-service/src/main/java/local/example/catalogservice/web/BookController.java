package local.example.catalogservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import local.example.catalogservice.domain.Book;
import local.example.catalogservice.domain.BookService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("books")
@Slf4j
public class BookController {
	private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Iterable<Book> get() {
		log.info("Fetching the list of books in the catalog");
        return bookService.viewBookList();
    }

    @GetMapping("{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
    	log.info("Fetching the book with ISBN {} from the catalog", isbn);
    	return bookService.viewBookDetails(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid @RequestBody Book book) {
    	log.info("Adding a new book to the catalog with ISBN {}", book.getIsbn());
    	return bookService.addBookToCatalog(book);
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
    	log.info("Deleting book with ISBN {}", isbn);
    	bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("{isbn}")
    public Book put(@PathVariable String isbn, @Valid @RequestBody Book book) {
    	log.info("Updating book with ISBN {}", isbn);
    	return bookService.editBookDetails(isbn, book);
    }
}
