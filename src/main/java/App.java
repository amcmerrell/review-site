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

    get("/locations/:location_id/businesses/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Location myLocation = Location.find(Integer.parseInt(request.params(":location_id")));
      Business aBusiness = Business.find(Integer.parseInt(request.params(":id")));
      model.put("location", myLocation);
      model.put("business", aBusiness);
      model.put("template", "templates/business-reviews.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
