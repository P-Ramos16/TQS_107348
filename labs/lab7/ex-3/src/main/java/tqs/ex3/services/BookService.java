package tqs.ex3.services;

import org.springframework.stereotype.Service;

import tqs.ex3.repositories.BookRepo;
import tqs.ex3.models.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    final
    BookRepo bookRepository;

    public BookService(BookRepo bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book c) {
        return bookRepository.save(c);
    }

    public List<Book> listBooks() {

        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        else {
            return null;
        }
    }

    public Book getBookByModel(String title) {
        return bookRepository.findByTitle(title);
    }


    public boolean bookExistsByModel(String title) {
        return bookRepository.findByTitle(title) != null;
    }
}
