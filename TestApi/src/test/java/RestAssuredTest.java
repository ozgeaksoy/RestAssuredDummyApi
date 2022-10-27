import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {
    @Test
    public void singleUser()
    {
        when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(400)
                .body("data.email", equalTo("janet.weaver@reqres.in"));
                //.time(lessThan(1000L));
    }

    @Test
    public void listUsers()
    {
        when().get("https://reqres.in/api/users?page=2").
                then().statusCode(200)
                .body("data.id[1]",equalTo(8))
                .body("data.first_name", hasItem("Michael"));
                //.log().all();
    }

    /*public void createUsers()
    {
        RestAssured.post("https://reqres.in/api/users");
        post("{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}");
                //.then().statusCode(200)
                //.log().all();

    }*/

    @Test
    public void createUser()
    {
        baseURI ="https://reqres.in/api";

        /*String requestBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";*/

        Response response =null;

        User usr = new User();
        usr.job = "leader";
        usr.name = "morpheus";

        Gson gson = new Gson();
        String requestBody = gson.toJson(usr);


        try {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post("/users");
          User newUser = gson.fromJson(response.asString(),User.class);
          System.out.println("User Name:" + newUser.createdAt);

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("response:" + response.asString());


    }

    @Test
    public void login()
    {
        baseURI ="https://reqres.in/api";

        String requestBody = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";
        Response response =null;

        try {
             response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post("/login");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("response:" + response.asString());

    }

    @Test
    public void update()
    {
        baseURI ="https://reqres.in/api";

        String requestBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        Response response =null;

        try {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .put("/users/2");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("response:" + response.asString());
    }

    @Test
    public void delete()
    {

        when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .log().all();

    }

    @Test
    public void singleUserNotFound()
    {
        when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);


    }

}
