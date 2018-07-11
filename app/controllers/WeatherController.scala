/**
  * Created by Guy Guillaume on 4/4/2017.
  */

package controllers

import javax.inject._
import play.api.libs.json
import play.api._
import play.api.mvc._
import akka.util._
import play.api.libs.json._

import scala.concurrent.Future

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */


@Singleton
class WeatherController @Inject() extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

case class City(cityName:String){
    case class Weather(city:City,appid:String="96a8066e473de57377b1b78d8e4b9ec1"){
      val url =  f"http://api.openweathermap.org/data/2.5/weather?q=${city.cityName}&appid=$appid"
      println(url)
      }

    def getWeather(): JsValue ={
      val result = scala.io.Source.fromURL(Weather(this).url)
      val json = Json.parse(result.mkString)
      val windSpeed = (json \ "wind" \ "speed").get

      println(f"wind speed is at $windSpeed")
      json
    }

}


  def parseInput(s:String): List[(String,String)] = {

    s.split("/").map(_.split("=")).map{case Array(a,b) => (a,b)}.toList

  }

  def getCityWeather(xs:List[(String,String)]) : (City, JsValue) = {

val c = City(cityName=xs.head._1)
val w = c.getWeather()
    (c,w)

  }

  def index(city:String) =

  Action {
  Ok(
    City(parseInput(city).filter{ case (a,_) =>  a == "city"}.head._2).getWeather().toString()
  )

    //   Ok("Your name is test")
  }

  /*
   */


  /*
  def index = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = http.HttpEntity.Strict(akka.util.ByteString("Hello world 2 !"), Some("text/plain"))
    )
  }
  */
}
