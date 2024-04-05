package tqs.ex3.controllers;

import tqs.ex3.models.Book;
import tqs.ex3.services.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService service) {
        this.bookService = service;
    }


    @PostMapping("/add") public ResponseEntity<Book> createBook(@RequestBody Book oneBook) {
        HttpStatus status = HttpStatus.CREATED;
        Book saved = bookService.save(oneBook);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/list",  produces = "application/json")
    public List<Book> getAllBooks() {
        return bookService.listBooks();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long id) {
        Book book = bookService.getBook(id);
        
        if (book == null) {
            return ResponseEntity.badRequest().body(null);
        }
        
        return ResponseEntity.ok().body(book);
    }

}
