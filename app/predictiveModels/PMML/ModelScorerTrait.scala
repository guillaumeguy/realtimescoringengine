package predictiveModels.PMML


import com.google.inject._
import com.typesafe.config.ConfigFactory
import datamodel.Format.PredHash
import org.dmg.pmml.{FieldName, Model}
import org.dmg.pmml.regression.RegressionModel
import org.jpmml.evaluator.{FieldValue, ModelEvaluator}

import scala.collection.JavaConversions._
import scala.collection.immutable.Map
import play.api._
import play.api.mvc._

import scala.concurrent.Future
// import javax.inject.Singleton
import play.api.libs.concurrent.Execution.Implicits.defaultContext


/**
  * Created by Guy Guillaume on 4/12/2017.
  */

@ImplementedBy(classOf[ModelScorer])
trait ModelScorerTrait {

  def predict(pred:PredHash): Double
  def getSummary(): String

}


@Singleton
class ModelScorer extends ModelScorerTrait {

  val debug = ConfigFactory.load.getString("debug.flag").toBoolean

  val model:ModelEvaluator[_ <: Model] = {
    val loc = ConfigFactory.load.getString("model.PMMLlocation")

    println("Loading model @", loc)
    loc match {
      case null => throw new Exception(f" $loc :PMML location unknown")
      case a => ModelLoader.load(a)
    }
  }

  def getSummary() = this.model.getSummary.toString

  override def predict(pred:PredHash) : Double = {
  //Future[Double] = {

    predictHelper(pred)
  //  scala.concurrent.Future {predictHelper(pred) }

  }

  def predictHelper(pred:PredHash): Double = {

    val inputFields = this.model.getInputFields()

    val args = inputFields.foldLeft(Map[FieldName,FieldValue]()){
      case (a,inputField) =>
        val inputFieldName  = inputField.getName()

        if(debug) println(f" Fetching $inputFieldName from PredMap...")

        // The raw (ie. user-supplied) value could be any Java primitive value
        val rawValue = pred.get(inputFieldName.getValue) match {
          case Some(a) if a.isInstanceOf[Int] => a.asInstanceOf[Int]
          case Some(a) if a.isInstanceOf[Double] => a.asInstanceOf[Double]
          case _ => throw new Exception(f"$inputFieldName is not provided in the Json!")
        }

        if(debug) println(f"... and got $rawValue}")

        // The raw value is passed through: 1) outlier treatment, 2) missing value treatment, 3) invalid value treatment and 4) type conversion
        val inputFieldValue:FieldValue = inputField.prepare(rawValue);

        a.+(inputFieldName -> inputFieldValue);
    }

    val results =  this.model.evaluate(args)
    val targetFields = this.model.getTargetFields
    val targetFieldValue = results.get(targetFields.head.getName).asInstanceOf[Double]

    println(targetFieldValue)
    targetFieldValue
  }



}