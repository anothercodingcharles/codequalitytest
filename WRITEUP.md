

### WRITE UP - THOUGHT PROCESS


1.- Read, analyze and understand document requirements.

2.- Getting familiar with the San Francisco API, and dataset fields representation

3.- Design url and query parameters to retrieve ALL food trucks using PostMan.

4.- To provide a more rich filtering (and since a numeric field for start/close times didn't exist) 
    decided to use start24 and end24 (Start time & End time in 24 hour format e.g. 12:30, 15:30, 02:04) along with SoQL to perform comparison operations on strings. 
    This decision was made because the same-length string representing numeric hours, leaded to a lexicographic comparison.
    
5.- Refactored query to retrieve only needed fields (name,address) of food trucks, sort output.

6.- With the queries working, moved on with the code.

7.- Decided to use maven as my main dependency manager, and Java 8.

8.- Thought on pagination before writing service, to avoid high payload responses.

8.- Took a bottom up process. Started by coding a simple HTTP URL connection retrieve data from API.

9.- Thinking of reusable code, defined a generic utility HTTPRequest class to perform http requests to any endpoint.

10.- Defined DTOs to decouple response structures and define what and how data
     is represented for a HTTPResponse and FoodTruck. Used JACKSON library for this
     
11.- Thinking of separation of concerns, created  a FoodTruckService to handle business logic and defined needed methods to achieve 
pagination—retrieve total count of food trucks  AND retrieve 10-page sized food trucks chunks.

12.- FoodTruckService used a LocalDateTime to compute day and hour. This made the code non deterministic.
In order to make the code testable, defined a datetimeprovider interface with NOW() method, NOW() method can be override to provided any datetime.

13.- Thinking on maintainability, designed an AppProperties class to read in properties. This can be modified to account for different testing envs in the future.

14.- Wrote main program with the output logic.


     
