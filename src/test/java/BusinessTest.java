import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import org.sql2o.*;

public class BusinessTest {
  Location testLocation;
  Business testBusness;
  @Before
    public void setUp() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/review_site_test", null, null);
      testLocation = new Location("Oregon", "Portland");
      testLocation.save();
      testBusness = new Business("Applebees",testLocation.getId());
      testBusness.save();
    }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBusinessQuery = "DELETE FROM businesses *;";
      String deleteLocationsQuery = "DELETE FROM locations *;";
      String deleteReviewsQuery = "DELETE FROM reviews *;";
      con.createQuery(deleteBusinessQuery).executeUpdate();
      con.createQuery(deleteLocationsQuery).executeUpdate();
      con.createQuery(deleteReviewsQuery).executeUpdate();
    }
  }

  @Test
  public void business__instantiatesCorrectly_true(){
    assertTrue(testBusness instanceof Business);
  }

  @Test
  public void getName_getsName_True(){
    assertTrue(testBusness.getName().equals("Applebees"));
  }
  @Test
  public void getLocationId_getsLocationId_True(){
    assertTrue(testBusness.getLocationId() == testLocation.getId());
  }
  @Test
  public void save_savesIntoDatabase_true(){
    assertTrue(Business.all().get(0).equals(testBusness));
  }
  @Test
  public void save_savesIdIntoDatabase_true(){
    assertTrue(Business.all().get(0).getId() == testBusness.getId());
  }
  @Test
  public void find_findsInstance_True(){
    Business testBusness2 = new Business("Epicodus",testLocation.getId());
    testBusness2.save();
    assertTrue(Business.find(testBusness.getId()).equals(testBusness));
  }
  @Test
  public void updateName_updatesNameOfBusiness_true() {
    testBusness.updateName("Epicodus");
    assertEquals("Epicodus", Business.find(testBusness.getId()).getName());
  }

  @Test
  public void updateLocationId_updatesLocationIdOfBusiness_true() {
    Location testLocation2 = new Location("Oregon", "Beaverton");
    testLocation2.save();
    testBusness.updateLocationId(testLocation2.getId());
    assertEquals(testLocation2.getId(), Business.find(testBusness.getId()).getLocationId());
  }

  @Test
  public void delete_deletesBusiness_true() {
    int testId = testBusness.getId();
    testBusness.delete();
    assertNull(Business.find(testId));
  }

  @Test
  public void getReviews_returnsReviews_true() {
    Business testBusness2 = new Business("Epicodus", testLocation.getId());
    testBusness2.save();
    Review testReview = new Review(5,"Was a great place to dine",testBusness.getId());
    testReview.save();
    Review secondReview = new Review(1, "Not that great.", testBusness2.getId());
    secondReview.save();
    List<Review> testList = testBusness2.getReviews();
    assertTrue(!(testList.contains(testReview)) && testList.contains(secondReview));
  }
}
