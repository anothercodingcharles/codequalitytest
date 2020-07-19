package com.oraclemdc.foodtruckfinder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oraclemdc.foodtruckfinder.dto.FoodTruck;
import com.oraclemdc.foodtruckfinder.dto.HttpResponse;
import com.oraclemdc.foodtruckfinder.utils.AppProperties;
import com.oraclemdc.foodtruckfinder.utils.DateTimeProvider;
import com.oraclemdc.foodtruckfinder.utils.HTTPRequestUtils;
import org.json.JSONArray;
import org.json.JSONException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FoodTruckService {

    private final DateTimeProvider dateTimeProvider;

    //read in base url from properties file
    private static final String BASE_URL = AppProperties.getProperty("baseUrl");

    //read in base url from properties file
    //identifies the application, with this, the application is guaranteed access to it's own pool of requests.
    private static final String APP_TOKEN = AppProperties.getProperty("appToken");

    //this is used to destructure JSON object into DTOs
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * @param dateTimeProvider DateTimeProvider implementation overriding a
     *                         LocalDateTime object.
     */
    public FoodTruckService(DateTimeProvider dateTimeProvider) throws IOException {
        this.dateTimeProvider = dateTimeProvider;
    }

    /**
     * @return Current time in 24 hour format e.g. 00:00, 12:00 etc.
     */
    private String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.dateTimeProvider.now().format(formatter);
    }

    /**
     * @return dayOfWeek Number corresponding to day of week: 0=Sunday, 1=Monday, etc.
     */
    private int getDayOfWeek() {
        return this.dateTimeProvider.now().getDayOfWeek().getValue() % 7;
    }

    /**
     * This method defines the needed HTTP method, headers & query parameters (SoQL queries).
     * Expects a JSON containing the number of food trucks available at a given time.
     *
     * @return The number of available food trucks at a given datetime.
     */
    public int getFoodTruckCount() {

        //Define Socrata Query Language parameters Map
        Map<String, String> parameters = new HashMap<>();
        parameters.put("$select", "count(*)");
        parameters.put("$where", "start24 <=  '" + getTime() + "' AND end24 >= '" + getTime() + "' AND dayorder = " + getDayOfWeek());

        int foodTruckCount;
        HttpResponse httpResponse = null;


        //call HTTPRequestUtils request method to perform the request
        try {

            //expects a HTTPResponse DTO
            httpResponse = HTTPRequestUtils.request(BASE_URL, parameters, getDefaultHeaders(), "GET", null, 4000);
            JSONArray jsonArray = new JSONArray(httpResponse.getPayload());
            foodTruckCount = (jsonArray.length() > 0) ? jsonArray.getJSONObject(0).getInt("count") : 0;

        } catch (JSONException e) {
            System.out.println(httpResponse.getPayload());
            foodTruckCount = 0;

        } catch (IOException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : e.getLocalizedMessage());
            foodTruckCount = 0;
        }
        return foodTruckCount;
    }

    /**
     * This method defines the needed http method, headers, query parameters (SoQL queries)
     * to perform an HTTP request that expects an Array of JSON Objects representing the food trucks available
     * at a given time.
     *
     * @param offset   The index of the result array where to start the returned list of results.
     * @param pageSize The number of results to return
     * @return A list of available food trucks at a given datetime.
     */

    public FoodTruck[] getFoodTrucks(int offset, int pageSize) {

        //These parameters form the rich filtering query parameters using the Socrata Query Language
        //Expects the Applicant,Location of food trucks that open/close between current time, sorted by ascending order
        //limited by 10. Pagination is handled by sending a limit,offset parameters
        Map<String, String> parameters = new HashMap<>();
        parameters.put("$select", "Applicant,location");
        parameters.put("$where", "start24 <=  '" + getTime() + "' AND end24 >= '" + getTime() + "' AND dayorder = " + getDayOfWeek());
        parameters.put("$order", "Applicant ASC");
        parameters.put("$limit", Integer.toString(pageSize));
        parameters.put("$offset", Integer.toString(offset));


        HttpResponse httpResponse;
        FoodTruck[] foodTrucks;
        //call HTTPRequestUtils request to perform the request
        try {

            httpResponse = HTTPRequestUtils.request(BASE_URL, parameters, getDefaultHeaders(), "GET", null, 4000);

            if (httpResponse.getResponseCode() != HttpsURLConnection.HTTP_OK)
                foodTrucks = new FoodTruck[]{};
            else
                foodTrucks = OBJECT_MAPPER.readValue(httpResponse.getPayload(), FoodTruck[].class);

        } catch (IOException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : e.getLocalizedMessage());
            foodTrucks = new FoodTruck[]{};
        }
        return foodTrucks;
    }

    /**
     * @return default headers content type and app token
     */
    private Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("X-App-Token", APP_TOKEN);
        return headers;
    }


}
