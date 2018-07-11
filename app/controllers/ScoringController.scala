
/**
  * Created by Guy Guillaume on 4/5/2017.
  * Copyright Guillaume Guy
  */


package controllers

import javax.inject._

import play.api.mvc._
import datamodel.Format.MergedFormat

import play.api.db.Database
import play.api.libs.json.{JsPath, Json, Reads}
import play.api.libs.functional.syntax._

import scala.concurrent.Future

import org.dmg.pmml.regression.RegressionModel
/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */

/*
@Singleton
class ScoringController @Inject()(db: Database) extends Controller {




  /**
    * This parses the Json text into a case class. It has to match the incoming json file
    *  It can also be used to parse fields into
    *  check https://www.playframework.com/documentation/2.5.x/ScalaJsonHttp to run checks and validation
    */
  implicit val predReads: Reads[MergedFormat] = (
     (JsPath \ "ID").read[Int] and
  //   (JsPath \ "SPEND_12M").read[Double] and
     (JsPath \ "RECENCY").read[Double] and
     (JsPath \ "LAST_EMAIL_OPEN").read[Double] and
     (JsPath \ "HOUSEHOLD_INCOME").read[Double] and
     (JsPath \ "CHANNEL_ACQUISITION").read[String]
    )(MergedFormat.apply _ )

  def index = Action(BodyParsers.parse.json[MergedFormat]) { request =>

    val debug = true
    // `request.body` contains a fully validated `Place` instance.
    val body = request.body

    val preds = body.toPredFormat.toMap

    if(debug) println("preds=",preds)

    val model = ScoringModel.load[RegressionModel]()

    if(debug) println("model=",model.getSummary)

    val prob = ScoringModel.predict(model,preds)

    if(debug) println("prob:",prob)

    Ok(Json.obj("status" ->"OK", "message" -> (" First Variable received : '"+ preds.head +f"'  Approval rate $prob ") ))
  }
   }

*/