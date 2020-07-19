package com.oraclemdc.foodtruckfinder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
  FoodTruck DTO sole purpose is to decouple response structures and define what and how data
  is represented.
 */
public class FoodTruck {

    @JsonProperty("Applicant")
    private String name;
    @JsonProperty("location")
    private String location;

    public FoodTruck() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
