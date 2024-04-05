package tqs.ex3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.ex3.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {

    public Optional<Book> findById(Long Id);

    public Book findByTitle(String title);

    public List<Book> findAll();

}
