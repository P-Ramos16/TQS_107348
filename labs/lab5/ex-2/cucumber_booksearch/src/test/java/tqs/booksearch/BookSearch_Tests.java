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

import io.cucumber.datatable.DataTable;
import java.util.Map;
import io.cucumber.java.Before;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.commons.lang3.time.DateUtils;
 
public class BookSearch_Tests {
	Library library = new Library();
	List<Book> result = new ArrayList<>();

	@Before
    public void setUp() {
        library = new Library();
        result = new ArrayList<>();
    }

	@Given("I have a list of books")
	public void haveBooksInTheStoreByMap(DataTable table) {
		List<Map<String, String>> rows = table.asMaps(String.class, String.class);

		for (Map<String, String> columns : rows) {
			try {
				library.addBook(new Book(columns.get("Title"), columns.get("Author"), DateUtils.parseDate(columns.get("Date"), new String[] {"yyyy-MM-dd-HH:mm+ss"})));
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@When("I search for books by author {string}")	
	public void i_search_for_books_by_author(String author) {
		result = library.findBookbyAuthor(author);
	}
 
	@When("I search for books by the date {string} to {string}")
	public void i_search_for_books_by_the_date(String datestart, String datefinish) {
		try {
			Date dateStart = DateUtils.parseDate(datestart, new String[] {"yyyy-MM-dd-HH:mm+ss"});
			Date dateFinish = DateUtils.parseDate(datefinish, new String[] {"yyyy-MM-dd-HH:mm+ss"});
			result = library.findBooks(dateStart, dateFinish);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			result = null;
		}
	}
 
	@When("I search for books by title {string}")
	public void i_search_for_books_by_title(String title) {
		result = library.findBookbyTitle(title);
	}
 
	@Then("I should find {int} books")
	public void verifyAmountOfBooksFound(int booksFound) {
		assertThat(result.size(), equalTo(booksFound));
	}
}