package tqs.ex3;

import tqs.ex3.controllers.BookController;
import tqs.ex3.models.Book;
import tqs.ex3.repositories.BookRepo;
import tqs.ex3.services.BookService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.Order;

@Testcontainers
@SpringBootTest
class RestControllerTest {
    @Container
    @Order(1)
    public static PostgreSQLContainer container = new PostgreSQLContainer()
      .withUsername("duke")
      .withPassword("password")
      .withDatabaseName("test");
  
    @Autowired
    private BookRepo bookRepository;
  
    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    @Order(2)
    static void properties(DynamicPropertyRegistry registry) {
      registry.add("spring.datasource.url", container::getJdbcUrl);
      registry.add("spring.datasource.password", container::getPassword);
      registry.add("spring.datasource.username", container::getUsername);
    }
  
    @Test
    @Order(3)
    void contextLoads() {
  
      Book book = new Book();
      book.setAuthor("Testcontainers");
  
      bookRepository.save(book);
  
      System.out.println("Context loads!");
    }
}