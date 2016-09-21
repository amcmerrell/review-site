import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class LocationTest {
  Location testLocation;

  @Before
    public void setUp() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/review_site_test", null, null);
      testLocation = new Location("Oregon", "Portland");
      testLocation.save();
    }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBusinessQuery = "DELETE FROM businesses *;";
      String deleteLocationsQuery = "DELETE FROM locations *;";
      con.createQuery(deleteBusinessQuery).executeUpdate();
      con.createQuery(deleteLocationsQuery).executeUpdate();
    }
  }

  @Test
  public void location_instantiatesCorrectly_true() {
    assertTrue(testLocation instanceof Location);
  }

  @Test
  public void getState_locationInstantiatesWithState_Oregon() {
    assertEquals("Oregon", testLocation.getState());
  }

  @Test
  public void getCity_locationInstantiatesWithCity_Portland() {
    assertEquals("Portland", testLocation.getCity());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    assertTrue(Location.all().get(0).equals(testLocation));
  }

  @Test
  public void save_assignsIdToObject_1() {
    Location savedLocation = Location.all().get(0);
    assertEquals(testLocation.getId(), savedLocation.getId());
  }

  @Test
  public void find_returnsLocationWithSameId_true() {
    Location secondLocation = new Location("North Carolina", "Raleigh");
    secondLocation.save();
    assertEquals(Location.find(secondLocation.getId()), secondLocation);
  }

  @Test
  public void updateCity_updatesLocationCity_true() {
    testLocation.updateCity("Salem");
    assertEquals("Salem", Location.find(testLocation.getId()).getCity());
  }

  @Test
  public void updateState_updatesLocationState_true() {
    testLocation.updateState("Washington");
    assertEquals("Washington", Location.find(testLocation.getId()).getState());
  }

  @Test
  public void delete_deletesLocation_true() {
    int testId = testLocation.getId();
    testLocation.delete();
    assertNull(Location.find(testId));
  }

  @Test
  public void getBusinesses_returnsBusinesses_true(){
    Location secondLocation = new Location("North Carolina", "Raleigh");
    secondLocation.save();
    Business testBusness = new Business("Applebees",testLocation.getId());
    testBusness.save();
    Business testBusness2 = new Business("Epicodus",secondLocation.getId());
    testBusness2.save();
    List<Business> testList = testLocation.getBusinesses();
    assertTrue(!(testList.contains(testBusness2)) && testList.contains(testBusness));
  }
}
