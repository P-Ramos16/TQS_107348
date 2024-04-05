package tqs.ex1;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThan;

import java.util.concurrent.TimeUnit;



/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test for exercice A
     */
    @Test public void list_all_todos_returns_200() {
        String url = "https://jsonplaceholder.typicode.com/todos";
        
        given().when().get(url).
            then().assertThat().
                statusCode(200);
    }

    /**
     * Rigorous Test for exercice B
     */
    @Test public void query_for_todo_4_returns_obj() {
        String key = "title";
        String value = "et porro tempora";

        String todoNum = "4";
        String url = "https://jsonplaceholder.typicode.com/todos";
        
        given().when().get(url + "/" + todoNum).
            then().assertThat().
                statusCode(200).
                    body(key, equalTo(value));
    }

    /**
     * Rigorous Test for exercice C
     */
    @Test public void list_all_todos_get_ids() {
        String key = "id";

        String url = "https://jsonplaceholder.typicode.com/todos";
        
        given().when().get(url).
            then().assertThat().
                statusCode(200).
                    body(key, hasItems(198, 199));
    }
    
    /**
     * Rigorous Test for exercice D
     */
    @Test public void list_all_todos_returns_before_2_seconds() {
        String url = "https://jsonplaceholder.typicode.com/todos";
        Long seconds = 2L;
        
        given().when().get(url).then().time(lessThan(seconds), TimeUnit.SECONDS);
    }
}
