import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import static apiConfigs.Data.*;
import static apiConfigs.EndPoint.*;

public class TrelloBoardUsingRestAssured extends BaseTest{

    @Test(priority=1)
    public void createANewOrganization_shouldReturn_newOrganization(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath= ORGANIZATION_API;
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("displayName","Test Trello RestAssured");
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        requestSpecification.header("Content-Type","application/json");
        response = requestSpecification.post();
        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        idOrganization = jsonPath.get("id");
        idMember = jsonPath.get("idMemberCreator");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test(priority=2)
    public void getOrganizationForMember_shouldReturn_organization(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath=GET_ORGANIZATION_FOR_MEMBER+idMember+"/organizations";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        response = requestSpecification.get();
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }


    @Test(priority=3)
    public void createBoardOnOrganization_shouldReturn_aBoard(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath= BOARD_API;
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("name","Test Board RestAssured");
        requestSpecification.queryParam("desc","Test Board RestAssured description");
        requestSpecification.queryParam("idOrganization",idOrganization);
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        requestSpecification.header("Content-Type","application/json");
        response = requestSpecification.post();
        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        idBoard = jsonPath.get("id");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test(priority=4)
    public void getBoardsOnOrganization_shouldReturn_aBoard(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath=ORGANIZATION_API+idOrganization+"/boards";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        response = requestSpecification.get();
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test(priority=5)
    public void createNewList_shouldReturn_aList(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath= LIST_API;
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("name","Test New List RestAssured");
        requestSpecification.queryParam("idBoard",idBoard);
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        requestSpecification.header("Content-Type","application/json");
        response = requestSpecification.post();
        response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();
        idList = jsonPath.get("id");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test(priority=6)
    public void getAllListOnBoard_shouldReturn_allLists(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath=BOARD_API+idBoard+"/lists";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        response = requestSpecification.get();
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test(priority=7)
    public void archiveAList_shouldBe_archived(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath= LIST_API +idList+"/closed";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("value",true);
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        response = requestSpecification.put();
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test(priority=8, description = "Delete a board")
    public void deleteABoard_shouldBe_deleted(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath= BOARD_API +idBoard;
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        response = requestSpecification.delete();
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @Test(priority=9, description = "Delete an organization")
    public void deleteAnOrganization_shouldBe_deleted(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.basePath= ORGANIZATION_API +idOrganization;
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.queryParam("key",API_KEY);
        requestSpecification.queryParam("token",API_TOKEN);
        response = requestSpecification.delete();
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }
}