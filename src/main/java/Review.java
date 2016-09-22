import org.sql2o.*;
import java.util.List;

public class Review{
  private int id;
  private int businessId;
  private int locationId;
  private int score;
  private String reviewerName;
  private String reviewTitle;
  private String reviewComment;

  public Review(int score, String reviewerName, String reviewComment, String reviewTitle, int businessId, int locationId){
    this.score = score;
    this.reviewerName = reviewerName;
    this.reviewTitle = reviewTitle;
    this.reviewComment = reviewComment;
    this.businessId = businessId;
    this.locationId = locationId;
  }

  public int getId(){
    return id;
  }

  public int getBusinessId(){
    return businessId;
  }

  public int getLocationId(){
    return businessId;
  }

  public String getReviewerName(){
    return reviewerName;
  }

  public String getReviewTitle(){
    return reviewTitle;
  }

  public String getReviewComment(){
    return reviewComment;
  }

  public int getScore(){
    return score;
  }

  @Override
  public boolean equals(Object someObject){
    if(!(someObject instanceof Review)){
      return false;
    }
    Review otherObject = (Review) someObject;
    return this.getReviewComment().equals(otherObject.getReviewComment()) &&
          this.getId() == otherObject.getId() &&
          this.getScore() == otherObject.getScore() &&
          this.getBusinessId() == otherObject.getBusinessId();
  }

  public static List<Review> all(){
    String sql = "SELECT * FROM reviews";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
              .executeAndFetch(Review.class);
    }
  }

  public void save(){
    String sql = "INSERT INTO reviews (businessId, score, reviewerName, reviewTitle, reviewComment, locationId) VALUES (:businessId, :score, :reviewerName, :reviewTitle, :reviewComment, :locationId)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
          .addParameter("businessId", this.businessId)
          .addParameter("locationId", this.locationId)
          .addParameter("score", this.score)
          .addParameter("reviewerName", this.reviewerName)
          .addParameter("reviewTitle", this.reviewTitle)
          .addParameter("reviewComment", this.reviewComment)
          .executeUpdate()
          .getKey();
    }
  }

  public static Review find(int id) {
    String sql = "SELECT * FROM reviews WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Review.class);
    }
  }

  public void updateScore(int score) {
    String sql = "UPDATE reviews SET score = :score WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("score", score)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateReviewComment(String reviewComment) {
    String sql = "UPDATE reviews SET reviewComment = :reviewComment WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("reviewComment", reviewComment)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    String sql = "DELETE FROM reviews WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
