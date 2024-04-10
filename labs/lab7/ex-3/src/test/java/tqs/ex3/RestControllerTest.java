package tqs.ex3;

import tqs.ex3.models.Book;
import tqs.ex3.repositories.BookRepo;

import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestControllerTest {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:14")
      .withUsername("ex3")
      .withPassword("TQS_Pass")
      .withDatabaseName("test");
  
    @Autowired
    private BookRepo bookRepository;
  
    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
      registry.add("spring.datasource.url", container::getJdbcUrl);
      registry.add("spring.datasource.password", container::getPassword);
      registry.add("spring.datasource.username", container::getUsername);
    }
  
    @Test
    @Order(1)
    void testCreateBook() {
      //  Create a book
      Book book = new Book();

      book.setId(1L);
      book.setAuthor("Josefino");
  
      //  Find the book
      Optional<Book> savedBook = bookRepository.findById(book.getId());

      //  Assert that the values of the inserted book and the found book are the same
      assertThat(savedBook.isPresent()).isTrue();
      assertThat(savedBook.get().getAuthor()).isEqualTo(book.getAuthor());
  
      System.out.println("Context loads!");
      System.out.println("Book saved!");
      System.out.flush();

    }
  
    @Test
    @Order(2)
    void testUpdateBook() {
      //  Create a book with the same ID as the one in the database
      Book book = new Book();
      book.setId(1L);
      book.setAuthor("Josefino");

      //  Find the one in the database
      Optional<Book> savedBook = bookRepository.findById(book.getId());

      //  Make sure the values are still the old ones
      assertThat(savedBook.isPresent()).isTrue();
      assertThat(savedBook.get().getAuthor()).isEqualTo(book.getAuthor());

      Book updatedBook = savedBook.get();

      //  Change a value in the book
      updatedBook.setAuthor("Joana");
  
      //  Update the book
      bookRepository.save(updatedBook);

      //  Find the updated book
      savedBook = bookRepository.findById(book.getId());

      //  Assert that the values are updated
      assertThat(savedBook.isPresent()).isTrue();
      assertThat(savedBook.get().getAuthor()).isEqualTo(updatedBook.getAuthor());
  
      System.out.println("Book updated!");
      System.out.flush();

    }
  
    @Test
    @Order(3)
    void testDeleteBook() {
      //  Create a book with the same ID as the one in the database
      Book book = new Book();
      book.setId(1L);
      book.setAuthor("Joana");

      //  Find the one in the database
      Optional<Book> savedBook = bookRepository.findById(book.getId());

      //  Make sure the values are still the old ones
      assertThat(savedBook.isPresent()).isTrue();
      assertThat(savedBook.get().getAuthor()).isEqualTo(book.getAuthor());
  
      //  Delete the book
      bookRepository.delete(book);

      //  Find the deleted book
      savedBook = bookRepository.findById(book.getId());

      //  Assert that the book is not present
      assertThat(savedBook.isPresent()).isFalse();
  
      System.out.println("Book deleted!");
      System.out.flush();
      
    }
}