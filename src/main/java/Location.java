import org.sql2o.*;
import java.util.List;

public class Location{
  private String state;
  private String city;
  private int id;

  public Location (String state, String city) {
    this.state = state;
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public String getCity() {
    return city;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherLocation) {
    if (!(otherLocation instanceof Location)) {
      return false;
    } else {
      Location newLocation = (Location) otherLocation;
      return this.getState().equals(newLocation.getState()) &&
        this.getCity().equals(newLocation.getCity()) &&
        this.getId() == newLocation.getId();
    }
  }

  public static List<Location> all() {
    String sql = "SELECT * FROM locations";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Location.class);
    }
  }

  public void save() {
    String sql = "INSERT INTO locations (state, city) VALUES (:state, :city)";
    try(Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery(sql, true)
      .addParameter("state", this.state)
      .addParameter("city", this.city)
      .executeUpdate().getKey();
    }
  }

  public static Location find(int id) {
    String sql = "SELECT * FROM locations WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Location.class);
    }
  }

  public void updateCity(String city) {
    update("city",city);
  }

  public void updateState(String state) {
    update("state",state);
  }

  private void update(String columnName, String value){
    String sql = "UPDATE locations SET " + columnName + " = :" + columnName + " WHERE id = :id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter(columnName, value)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    String sql = "DELETE FROM locations WHERE id = :id";
    String sql2 = "DELETE FROM businesses WHERE locationId = :locationId";
    String sql3 = "DELETE FROM reviews WHERE locationId = :locationId";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
      con.createQuery(sql2)
        .addParameter("locationId", this.id)
        .executeUpdate();
      con.createQuery(sql3)
          .addParameter("locationId", this.id)
          .executeUpdate();
    }
  }

  public List<Business> getBusinesses(){
    String sql = "SELECT * FROM businesses where locationId = :id";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
                .addParameter("id",this.id)
                .executeAndFetch(Business.class);
    }
  }
}
