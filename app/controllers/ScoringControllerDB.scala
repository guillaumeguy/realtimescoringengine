
/**
  * Created by Guy Guillaume on 4/5/2017.
  * Copyright Guillaume Guy
  */


package controllers

import connectors.DBConnector
import datamodel.Format.{Keys, PredHash}

import play.api.db.Database
import play.api.libs.json.{JsPath, Json, Reads}
import predictiveModels.PMML._
import javax.inject._

import com.typesafe.config.ConfigFactory
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase


/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */

class ScoringControllerDB @Inject()(@NamedDatabase("default") db: play.api.db.Database
                                    ,model:predictiveModels.PMML.ModelScorerTrait
                                   ) extends Controller {


  /*
@Singleton
class ScoringControllerDB (db: Database, model : predictiveModels.PMML.ModelScorerTrait
                                   ) extends Controller {



    case class Location(lat: Double, long: Double)

    implicit val locationReads: Reads[Location] = (
      (JsPath \ "lat").read[Double] and
        (JsPath \ "long").read[Double]
      )(Location.apply _ )
  */



  /**
    * This parses the Json text into a case class. It has to match the incoming json file
    *  It can also be used to parse fields into
    *  check https://www.playframework.com/documentation/2.5.x/ScalaJsonHttp to run checks and validation
    **/

  implicit val predReads: Reads[Keys] = (
    (JsPath \ "ID").read[Int].map(Keys)
      //   (JsPath \ "SPEND_12M").read[Double] and
    )


  def index = Action(BodyParsers.parse.json[Keys]) { request =>

    val debug = ConfigFactory.load.getString("debug.flag").toBoolean
    // `request.body` contains a fully validated `Place` instance.
    val body : Keys = request.body

    println("body:",body)

    val preds =  DBConnector.getPredictors(db,body) match {
      case Some(a) =>  a.toMergedFormat.toPredFormat.toMap
      case None => Map.empty[String,Any]
    }

  if(preds.isEmpty) Status(488)("Bad input")
// PredHash.empty

//  val preds = body.toPredFormat.toMap

    if(debug) println("preds=",preds)

  //  val model = new ScoringModel.load[RegressionModel]("./assets/RF_Model.pmml")

    if(debug) println("model=",model.getSummary)

    if(debug) println("starting scoring")
    val customerValue = model.predict(preds)
    if(debug) println("scoring done")

    if(debug) println("prob:",customerValue)

    Ok(Json.obj(
      "status" ->"OK", "message" ->
        (f" First Variable received : '"+ preds.head +f"'  Predicted value = $customerValue ")
      ,"customerValue" -> customerValue
    ))

  }
}