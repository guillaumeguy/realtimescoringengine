
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
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */


  def parseInput(s:String): List[(String,String)] = {

    s.split("/").map(_.split("=")).map{case Array(a,b) => (a,b)}.toList

  }

   def index = Action {
     Ok(views.html.index("Your new application is ready"))
     //   Ok("Your name is test")
   }



  def index4 = Action {
    Ok(views.html.index2("Not used"))
    //   Ok("Your name is test")
  }

  /*
   */

  def index3 = Action { request =>
  Ok("Got request:" + request)
    //  Ok("Got request [" + @id + "]")
  }


  def index2 = Action { request =>
    Ok("Got request [" + request.toString.substring(0,3) + "]")
  }


  import play.api.libs.concurrent.Execution.Implicits.defaultContext



  def indexFuture(file:String) = Action.async {

    def intensiveComputation():Future[Double] = {
      Thread.sleep(50)
      Future(math.Pi)
    }

    val lowMediumHigh = file.replaceAll("lol=","").length match {
      case a if a  < 5 => "low"
      case a if a > 10 => file + " is" + "high"
      case _ => "medium"
    }

    val l = parseInput(file)

    val futurePi: Future[Double] =   intensiveComputation()

    futurePi.map(i => Ok("Got result: " + i + " " + l.mkString("-") + " " + lowMediumHigh ))
  }

  /*
  def index = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = http.HttpEntity.Strict(akka.util.ByteString("Hello world 2 !"), Some("text/plain"))
    )
  }
  */
}
