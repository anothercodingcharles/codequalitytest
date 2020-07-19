package com.oraclemdc.foodtruckfinder;

import com.oraclemdc.foodtruckfinder.dto.FoodTruck;
import com.oraclemdc.foodtruckfinder.service.FoodTruckService;
import com.oraclemdc.foodtruckfinder.utils.AppProperties;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class FoodTruckFinder {

    //read in page size value from properties file
    public static final int PAGE_SIZE = Integer.parseInt(Objects.requireNonNull(AppProperties.getProperty("pageSize")));

    public static void main(String[] args) throws IOException {


        //Initialize FoodTruckService with DateTimeProviderImpl object
        FoodTruckService foodTruckService = new FoodTruckService(LocalDateTime::now);

        //Compute the number of open food trucks
        int totalResults = foodTruckService.getFoodTruckCount();
        if (totalResults == 0) {
            System.out.println("NO FOOD TRUCKS OPEN RIGHT NOW");
            return;
        }

        /* 10-sized food truck pagination is used to prevent a high payload.
           User can query next page by interacting with command line instructions.
         */

        int numberOfPages = (totalResults / PAGE_SIZE) + (totalResults % PAGE_SIZE > 0 ? 1 : 0);
        int pageNumber = 0;

        String input;
        Scanner sc = new Scanner(System.in);
        do {
            //print header
            System.out.println(String.format("%-80s  %-20s", "NAME", "ADDRESS"));
            //get and display ith page
            FoodTruck[] foodTrucks = foodTruckService.getFoodTrucks(pageNumber * PAGE_SIZE, PAGE_SIZE);
            Arrays.stream(foodTrucks).forEach(foodTruck -> System.out.println(String.format("%-80s  %-20s", foodTruck.getName(), foodTruck.getLocation())));
            //update next page number
            pageNumber++;

            //display instructions to query next page
            if (pageNumber < numberOfPages) {
                System.out.println("\nPlease PRESS ENTER to display more food trucks");
                System.out.println("PRESS q followed by ENTER to exit");
                input = sc.nextLine().trim();
            } else
                break;

        } while (!"q".equals(input));

    }

}
