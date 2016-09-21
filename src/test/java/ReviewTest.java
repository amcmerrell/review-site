import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class ReviewTest {
  Location testLocation;
  Business testBusness;
  Review testReview;
  @Before
    public void setUp() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/review_site_test", null, null);
      testLocation = new Location("Oregon", "Portland");
      testLocation.save();
      testBusness = new Business("Applebees",testLocation.getId());
      testBusness.save();
      testReview = new Review(5,"Was a great place to dine",testBusness.getId());
      testReview.save();
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
  public void save_savesIdIntoDatabase_true(){
    assertTrue(Review.all().get(0).getId() == testReview.getId());
  }

  @Test
  public void find_returnsReviewWithSameId_true() {
    Review secondReview = new Review(1, "Not that great.", testBusness.getId());
    secondReview.save();
    assertEquals(Review.find(secondReview.getId()), secondReview);
  }

  @Test
  public void updateScore_updatesScoreOfReview_true() {
    testReview.updateScore(3);
    assertEquals(3, Review.find(testReview.getId()).getScore());
  }

  @Test
  public void updateReviewComment_updatesCommentOfReview_true() {
    testReview.updateReviewComment("Was NOT a great place to dine");
    assertEquals("Was NOT a great place to dine", Review.find(testReview.getId()).getReviewComment());
  }

  @Test
  public void delete_deletesReview_true() {
    int testId = testReview.getId();
    testReview.delete();
    assertNull(Review.find(testId));
  }
}
