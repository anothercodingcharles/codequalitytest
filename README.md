# FOOD TRUCK FINDER

This application takes in the food truck data from the San Francisco government’s API(https://data.sfgov.org/Economy-and-Community/Mobile-Food-Schedule/jjew-r69b) 
and displays the food trucks that are open at the time you run the application.

San Francisco API offers limit & offset fields to handle pagination. Thus, to prevent a high payload, the application displays NAME & ADDRESS of the open food trucks in chunks of 10. 
With this ,the search is restricted and performs better since has a smaller json payload coming back. 

# APPLICATION BUILDING BLOCKS

* FoodTruckService class that encapsulates the application's business logic.

* FoodTruck and HTTPResponse DTO (Data Transfer Object) classes to simplify manipulation of HTTP response data by not having to use structure-specific APIs (i.e., for JSON, XML, etc.)

* Utility classes/interfaces

    * HTTPRequest class to execute requests and coordinate responses to HTTPResponse DTO class.
    * DateTimeProvider interface that provides a way to create a LocalDateTime object using now() method. now() can be overridden to provide any localtime , thus   making the program testable.
    *  AppProperties class to handle de reading of properties such as apptoke, baseurl, pagesize defined in app.properties
    * An App main program in charge of the command line printing logic.



### INSTALLATION
 Download the source folder from the repo.
 
### REQUIREMENTS 
Java required (Code is written in Java 8) 
   
    if not installed download corresponding JDK from https://www.oracle.com/mx/java/technologies/javase/javase-jdk8-downloads.html 
   
   The project is ready to run but, in case you want to build the project on your own 
           Maven dependency tool manager was used. Maven is a Java tool, so you must have Java installed in order to proceed.
          
           
    if not installed download Maven https://maven.apache.org/download.cgi 
           and follow installation instructions https://maven.apache.org/install.html
           
### RUN OSX
   In order to run the application, download the folder and then cd to the folder directory in your command line.
     You may test the compiled and packaged JAR with the following command:
    
      java -cp target/FoodTruckFinder-1.0-SNAPSHOT.jar com.oraclemdc.foodtruckfinder.FoodTruckFinder


### BUILD 
   Execute the following command. This will generate a .jar file under /target.
        
        mvn package

# CONSIDERATIONS
If this app is used by a large audience in a PROD environment, to speed up the request of San Francisco API data  a cache would be into consideration. 


