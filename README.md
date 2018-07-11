[<img src="https://img.shields.io/travis/playframework/play-scala-starter-example.svg"/>](https://travis-ci.org/playframework/play-scala-starter-example)


`Authors:  
Guillaume Guy
`

# A Scala Realtime Scoring Engine

This code base is using Scala's Play to deploy PMML models into a RESTful interface, making it easy for anybody across the organization to get scores real time. The current framework accepts any PMML as inputs (as long as they are accepted by the JPMML library cited below).

The dataprep will need to be converted to Scala unless it's a PMML-supported operation.
- PMML Library:  JPMML https://github.com/jpmml  
- Play framework can be consulted here: https://www.playframework.com/documentation/latest/Home for more details.


## Requirements

 - mysql 5.5.54 MySQL Community Server - Any database with a JDBC connector would work as an alternative 
 - sbt version 0.13 or above
 - Scala 2.11 or above
 - A good IDE (IntelliJ is recommended)
 
## Set up
 
 - Download the required components (see above)
 - Update conf/application.conf (database / password)
 - Add PMML object into ./assets/ (a RF_Model.pmml is provided out of the box for testing) 
 - Create a dummy database (`marketing` in my case) and a dummy table (`predictors`). Populate it with dataset from data (`sql + csv` files included)
 
## Running

Run this using [sbt](http://www.scala-sbt.org/).  If you downloaded this project from http://www.playframework.com/download then you'll find a prepackaged version of sbt in the project directory:

```
sbt

compile

run -Ddb.default.password=mypasswordhere

```

And then go to http://localhost:9000 to see the running web application.


## Controllers

### Out of the box Controllers

- HomeController.scala:

  Shows how to handle simple HTTP requests.

- AsyncController.scala:

  Shows how to do asynchronous programming when handling a request.

- CountController.scala:

  Shows how to inject a component into a controller and use the component when
  handling requests.


### Predictive Controllers

- ScoreControllerDB.scala:

  This is the main controller to be used that handles API requests. 
  
  Once running, you can test it this way: 
  
  ```curl --header "Content-type: application/json"  --request POST  --data '{"ID":12}'  http://localhost:9000/scoreDB```


- FormController.scala:

  This is a controller that has a form system. Use the browser to connect to it and test the model. Default address is:  localhost:9000/customer 
    

## Components

- connectors:

  Handles database connector

- model:

  Handle case classes definition and dataprep

- predictiveModels.PMML:

  Handle loading of the predictive models and convertion between .pmml files and scala classes. Also implement scoring routine

- views:

  Handle loading of the models and convertion between .pmml files and scala classes. Also implement scoring routine


## Filters

- Filters.scala:

  Creates the list of HTTP filters used by your application.

- ExampleFilter.scala

  A simple filter that adds a header to every response.


## Routes

- routes file handles incoming requests and direct them toward the right controller

## TO DO
  - Implement handling of categorical variables
  - Implement Unit / Integration tests 
  - Implement module executionrules