package tqs.booksearch;
 
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import tqs.booksearch.Book;
import tqs.booksearch.Library;
 
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
public class BookSearch_Tests {
	Library library = new Library();
	List<Book> result = new ArrayList<>();
 
	@Given(".+book with the title '(.+)', written by '(.+)', published in (.+)")
	public void addNewBook(final String title, final String author, final Date published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}
 
	@When("^the customer searches for books published between (\\d+) and (\\d+)$")
	public void setSearchParameters(final Date from, final Date to) {
		result = library.findBooks(from, to);
	}
 
	@Then("(\\d+) books should have been found$")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}
 
	@Then("Book (\\d+) should have the title '(.+)'$")
	public void verifyBookAtPosition(final int position, final String title) {
		assertThat(result.get(position - 1).getTitle(), equalTo(title));
	}
}