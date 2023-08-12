FSB Take Home Challenge
------------------------

This project creates a simple Java application to manage Games in a game service manager
environment. It is built using spring boot framework and has a rest endpoints exposed to
facilitate all CRUD operation to manage games.

Prerequistes:
-------------
This app requires Java 17 installed on your environment and also mvn installed if you want to build the 
Jar file locally rather than using the pre built jar file.

How To Use
-----------
Follow the below steps to run the app locally

- git clone project
- cd into Project
- java -jar /target/technicalChallenge-0.0.1-SNAPSHOT.jar
- The app will start and available on localhost:8080

Follow the below steps to build and run the app locally

- git clone project
- cd into Project
- mvn clean package
- the jar file will be available on the target folder
- java -jar /target/technicalChallenge-0.0.1-SNAPSHOT.jar
- The app will start and available on localhost:8080


Rest End Points:
----------------

 - POST  /api/v1/creategame   allows us to create/insert a new game
      
       ````
          {
            "name" : "COD 6",
            "dateOfCreation" : "2021/07/16",
            "active" : false
           }
       ````
 -  GET  localhost:8080/api/v1/getgame/{game name} this endpoint allows us to get a game
    from the cache by passing game name

 - GET localhost:8080/api/v1/getallgames this endpoint allow us to query and
   get all existing games on the cache

 - DELETE localhost:8080/api/v1/deletegame/{game name} this endpoint allows us to delete a 
   game from the cache by passing the game name

 All the above exposed rest apis will respond with the appropriate http status code depending on
 if the request was successful or if the request was an error. The below response json will be also
 attached on the response stating if the request was successful or not and reason for it.

              ````
              {
              "status": "SUCCESS",
              "reason": "Game created!"
              }
              ````
 


  Application Architecture
  ------------------------

 As stated above, the application makes use of spring boot framework to bootstrap a restful service to 
 simulate a game service manager environment. It has a controller class, model class and couple pojo classes
 to facilitate rest endpoints. All the business logic is handled in the service class with implements the Game
 service interface.
 

 Data Structure Cache
 --------------------

For this project, I have decided to use CopyOnWriteArrayList which is a Java concurrent data structure that allows
concurrent reads on the data structure. Compared to other concurrent data structures, it has a more expensive memory
footprint if there are more  modifications than reads on it. But for more reads than updates, it is a winner when compared
to other list based concurrent data structures. For this project, I have assumed, we will be doing more reads that updates
and CopyOnWriteArrayList would be the right choice of memory cache to use for concurrent access and better performance.


 Future Project Extensions
 -------------------------
 
ENV Configs: multiple config files for each environment could be added to provide environment specific configs for the app

 CI/CD: If the app was to get added to build pipeline, we could add Docker file for building docker image and use Jenkins file to
        facilitate that building of docker image. After that we could be adding Kubernetes Yaml file for cloud native deployments.
 
 Memory Cache: If the app was anticipated to get more load, we could make use of ConcurrentHashMaps, which is a key-value based data
               structure that could handle more concurrent operations without any concurrency issues.