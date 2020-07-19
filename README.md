# FOOD TRUCK FINDER

This application takes in the food truck data from the San Francisco government’s API(https://data.sfgov.org/Economy-and-Community/Mobile-Food-Schedule/jjew-r69b) 
and displays the food trucks that are open at the time you run the application.

San Francisco API offers limit & offset fields to handle pagination. Thus, to prevent a high payload, the application displays NAME & ADDRESS of the open food trucks in chunks of 10. 
With this ,the search is restricted and performs better since has a smaller json payload coming back. 

# APPLICATION BUILDING BLOCKS

1.- A FoodTruckService that encapsulates the application's business logic.

2.- DTOs (FoodTruck,HTTPResponse) to simplify manipulation of HTTP response data by not having to use structure-specific APIs (i.e., for JSON, XML, etc.)

3.- Utility classes

  HTTPRequest class to execute the HTTP Request , control transactions and coordinating responses to HTTPResponse DTO
  A DateTimeProvider interface that provides a way to create LocalDateTime object, now() method  can be overridden to provide any localtime , thus making the    program testable.
  An AppProperties class to handle de reading of properties such as apptoke,baseurl,pagination pagesize defined in app.properties

4.- A App main program in charge of the command line printing logic.


## HOW TO RUN ON OSx

### INSTALLATION
 Download the source folder from the repo  and then change to the folder directory in your command line. 
### REQUIREMENTS 
   Java required (Code is written in Java 8) 
    
    Head to https://www.oracle.com/mx/java/technologies/javase/javase-jdk8-downloads.html and dowload JDK
   Projects is ready to run but, in case you want to build the project on your own 
           Maven is used. Maven is a Java tool, so you must have Java installed in order to proceed.
          
           
           download Maven https://maven.apache.org/download.cgi 
           follow installation instructions https://maven.apache.org/install.html
### RUN
   In order to run the application, download the folder and then change to the folder directory in your command line.
     You may test the compiled and packaged JAR with the following command:
    
     java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.App

### BUILD 
   Execute the following command. This will generate a .jar file under /target.
        
        mvn package

# CONSIDERATIONS
If this app is used by a large audience in a PROD environment, to speed up the request of San Francisco API data  a cache would be into consideration. 


