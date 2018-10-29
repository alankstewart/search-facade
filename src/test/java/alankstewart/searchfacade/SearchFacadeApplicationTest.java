package alankstewart.searchfacade;

import alankstewart.searchfacade.shared.filter.Filter;
import alankstewart.searchfacade.shared.filter.Filter.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static alankstewart.searchfacade.shared.filter.Filter.Operator.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SearchFacadeApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchFacadeApplicationTest {

    @LocalServerPort
    int port;

    @Test
    public void shouldReturnEventById() {
        given()
                .port(port)
                .when()
                .get("/events/{id}", "507f191e810c19729de8aae3")
                .then()
                .statusCode(200)
                .assertThat()
                .body("user", equalTo("user4@sample.io"));
    }

    @Test
    public void shouldFailToFindUnknownEvent() {
        given()
                .port(port)
                .when()
                .get("/events/{id}", "com.foo.bar")
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldReturnUserById() {
        given()
                .port(port)
                .when()
                .get("/users/{id}", "507f191e810c19729de860e0")
                .then()
                .statusCode(200)
                .body("user", equalTo("user1@sample.io"));
    }

    @Test
    public void shouldFailToFindUnknownUser() {
        given()
                .port(port)
                .when()
                .get("/user/{id}", "com.foo.bar")
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldSearchEventsByValueWithMultipleFilters() {
        Filter filter1 = new Filter().setAttribute("user").setOperator(eq).setValue("user4@sample.io");
        Filter filter2 = new Filter().setAttribute("type").setOperator(eq).setValue("LOGIN");

        given()
                .queryParam("filter", filter1, filter2)
                .port(port)
                .when()
                .get("/events/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .and()
                .body("user[0]", equalTo("user4@sample.io"))
                .and()
                .body("type[0]", equalTo("LOGIN"));
    }

    @Test
    public void shouldSearchEventsByRange() {
        Filter filter = new Filter()
                .setAttribute("time")
                .setOperator(eq)
                .setRange(new Range().setFrom(1262542260000L).setTo(1262628660000L));

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/events/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(3))
                .and()
                .body("user[0]", equalTo("user4@sample.io"))
                .and()
                .body("user[1]", equalTo("user3@sample.io"))
                .and()
                .body("user[2]", equalTo("user4@sample.io"));
    }

    @Test
    public void shouldSearchEventsWithNoFilters() {
        given()
                .port(port)
                .when()
                .get("/events/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(8));
    }

    @Test
    public void shouldSearchEventsWithEmptyFilter() {
        given()
                .port(port)
                .when()
                .get("/events/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(8));
    }

    @Test
    public void shouldFailWithInvalidFilter() {
        Filter filter = new Filter();

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/users/search")
                .then()
                .statusCode(500);
    }

    @Test
    public void shouldNotFindEventsForUnknownValue() {
        Filter filter = new Filter().setAttribute("user").setOperator(eq).setValue("foo@bar");

        given()
                .queryParam("filter", filter)
                .port(port)
                .when()
                .get("/events/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    public void shouldSearchEventsByValueGreaterThanOrEqual() {
        Filter filter = new Filter()
                .setAttribute("time")
                .setOperator(gte)
                .setValue(1262542260000L);

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/events/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(3))
                .and()
                .body("user[0]", equalTo("user4@sample.io"))
                .and()
                .body("user[1]", equalTo("user3@sample.io"))
                .and()
                .body("user[2]", equalTo("user4@sample.io"));
    }

    @Test
    public void shouldSearchEventsByValueLessThanOrEqual() {
        Filter filter = new Filter()
                .setAttribute("time")
                .setOperator(lte)
                .setValue(1262513460000L);

        given()
                .port(port)
                .when()
                .queryParam("filter", filter)
                .get("/events/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(5));
    }

    @Test
    public void shouldSearchEventsByUser() {
        Filter filter = new Filter().setAttribute("user").setOperator(eq).setValue("user4@sample.io");

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/events/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    public void shouldSearchUsersByUser() {
        Filter filter = new Filter().setAttribute("user").setOperator(eq).setValue("user4@sample.io");

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/users/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .and()
                .body("workstation[0]", equalTo("192.168.1.13"))
                .and()
                .body("_id[0]", equalTo("507f191e810c19729de860e3"));
    }

    @Test
    public void shouldSearchUsersWithNoFilters() {
        given()
                .port(port)
                .when()
                .get("/users/search")
                .then()
                .statusCode(200)
                .body("size()", equalTo(4));
    }

    @Test
    public void shouldFailWithValueAndRange() {
        Filter filter = new Filter()
                .setAttribute("user")
                .setOperator(eq)
                .setValue("user4@sample.io")
                .setRange(new Range().setFrom(0).setTo(2L));

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/events/search")
                .then()
                .statusCode(500);
    }

    @Test
    public void shouldFailWithInvalidRange() {
        Filter filter = new Filter()
                .setAttribute("time")
                .setOperator(eq)
                .setRange(new Range().setFrom(2L).setTo(0));

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/events/search")
                .then()
                .statusCode(500);
    }

    @Test
    public void shouldFailWithGteOperatorAndRange() {
        Filter filter = new Filter()
                .setAttribute("user")
                .setOperator(gte)
                .setRange(new Range().setFrom(0).setTo(2L));

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/events/search")
                .then()
                .statusCode(500);
    }

    @Test
    public void shouldFailWithLteOperatorAndRange() {
        Filter filter = new Filter()
                .setAttribute("user")
                .setOperator(lte)
                .setRange(new Range().setFrom(0).setTo(2L));

        given()
                .port(port)
                .queryParam("filter", filter)
                .when()
                .get("/events/search")
                .then()
                .statusCode(500);
    }
}
