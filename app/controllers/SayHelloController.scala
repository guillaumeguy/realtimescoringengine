
/**
  * Created by Guy Guillaume on 4/5/2017.
  * Copyright Guillaume Guy
  */


package controllers

  import javax.inject._
  import play.api._
  import play.api.mvc._
  import akka.util._

  import scala.concurrent.Future

  /**
    * This controller creates an `Action` to handle HTTP requests to the
    * application's home page.
    */


  @Singleton
  class SayHelloController @Inject() extends Controller {

    /**
      * Create an Action to render an HTML page with a welcome message.
      * The configuration in the `routes` file means that this method
      * will be called when the application receives a `GET` request with
      * a path of `/`.
      */
    def sayHello = Action(parse.json) { request =>
      (request.body \ "name").asOpt[String].map { name =>
        Ok("Hello " + name)
      }.getOrElse {
        BadRequest("Missing parameter [name]")
      }
    }
  }

