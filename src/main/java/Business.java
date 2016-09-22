import org.sql2o.*;
import java.util.List;
import java.lang.Math;

public class Business{
  private int id;
  private int locationId;
  private String name;

  public Business(String name, int locationId){
    this.locationId = locationId;
    this.name = name;
  }

  public int getId(){
    return id;
  }

  public int getLocationId(){
    return locationId;
  }

  public String getName(){
    return name;
  }

  @Override
  public boolean equals(Object someObject){
    if(!(someObject instanceof Business)){
      return false;
    }
    Business otherObject = (Business) someObject;
    return this.getName().equals(otherObject.getName()) &&
          this.getId() == otherObject.getId() &&
          this.getLocationId() == otherObject.getLocationId();
  }

  public static List<Business> all(){
    String sql = "SELECT * FROM businesses";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
              .executeAndFetch(Business.class);
    }
  }

  public void save(){
    String sql = "INSERT INTO businesses (name, locationId) VALUES (:name, :locationId)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .addParameter("locationId", this.locationId)
          .executeUpdate()
          .getKey();
    }
  }

  public static Business find(int id){
    String sql = "SELECT * FROM businesses WHERE id = :id";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
          .addParameter("id",id)
          .executeAndFetchFirst(Business.class);
    }
  }

  public void updateName(String name) {
    String sql = "UPDATE businesses SET name = :name WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateLocationId(int locationId) {
    String sql = "UPDATE businesses SET locationId = :locationId WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("locationId", locationId )
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    String sql = "DELETE FROM businesses WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public List<Review> getReviews() {
    String sql = "SELECT * FROM reviews WHERE businessId = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Review.class);
    }
  }

  public int calcNumReviews() {
    return this.getReviews().size();
  }

  public double calcAverage(){
    List<Review> reviews = this.getReviews();
    double numberOfReviews = (double) this.calcNumReviews();
    double sum = 0.0;
    for(Review review :reviews){
      sum += (double) review.getScore();
    }
    return sum/numberOfReviews;
  }

  public int calcAverageInt(){
    return (int) Math.round(calcAverage() * 1d);
  }

  public double roundToHalf(){
    return Math.round(calcAverage() * 2) / 2.0;
  }
}
