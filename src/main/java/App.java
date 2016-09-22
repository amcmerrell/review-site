import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("locations",Location.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/locations/add", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String locationCity = request.queryParams("location-city");
      String locationState = request.queryParams("location-state");
      Location myLocation = new Location(locationState,locationCity);
      myLocation.save();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/locations/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int locationId = Integer.parseInt(request.params(":id"));
      Location updateLocation = Location.find(Integer.parseInt(request.params(":id")));
      model.put("location",updateLocation);
      model.put("template", "templates/location-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/locations/:id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int locationId = Integer.parseInt(request.params(":id"));
      Location updateLocation = Location.find(Integer.parseInt(request.params(":id")));
      String city = request.queryParams("location-city");
      String state = request.queryParams("location-state");
      updateLocation.updateCity(city);
      updateLocation.updateState(state);
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/locations/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Location deletedLocation = Location.find(Integer.parseInt(request.params(":id")));
      deletedLocation.delete();
      response.redirect("/");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/locations/:id/businesses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int locationId = Integer.parseInt(request.params(":id"));
      Location myLocation = Location.find(locationId);
      model.put("location", myLocation);
      model.put("businesses",myLocation.getBusinesses());
      model.put("template", "templates/location-businesses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/locations/id/businesses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int locationId = Integer.parseInt(request.queryParams("locationId"));
      response.redirect("/locations/"+ locationId +"/businesses");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/locations/:id/businesses/add", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int locationId = Integer.parseInt(request.params(":id"));
      String businessName = request.queryParams("business-name");
      Business myBusiness = new Business(businessName,locationId);
      myBusiness.save();
      response.redirect("/locations/"+ locationId +"/businesses");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/locations/:location_id/businesses/:id/reviews", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Location myLocation = Location.find(Integer.parseInt(request.params(":location_id")));
      Business aBusiness = Business.find(Integer.parseInt(request.params(":id")));
      model.put("location", myLocation);
      model.put("business", aBusiness);
      model.put("reviews", aBusiness.getReviews());
      model.put("template", "templates/business-reviews.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/locations/:location_id/businesses/:business_id/reviews/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int locationId = Integer.parseInt(request.params(":location_id"));
      int businessId = Integer.parseInt(request.params(":business_id"));
      Review deletedReview = Review.find(Integer.parseInt(request.params(":id")));
      deletedReview.delete();
      response.redirect("/locations/"+ locationId +"/businesses/" + businessId + "/reviews");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/locations/:location_id/businesses/:business_id/reviews/add", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int locationId = Integer.parseInt(request.params(":location_id"));
      int businessId = Integer.parseInt(request.params(":business_id"));
      int reviewScore = Integer.parseInt(request.queryParams("review-score"));
      String reviewerName = request.queryParams("reviewer-name");
      String reviewTitle = request.queryParams("review-title");
      String reviewComment = request.queryParams("review-comment");
      Review aReview = new Review(reviewScore,reviewerName,reviewComment,reviewTitle,businessId,locationId);
      aReview.save();
      response.redirect("/locations/"+ locationId +"/businesses/" + businessId + "/reviews");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
