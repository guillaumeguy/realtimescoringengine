  /*
  * Created by Guy Guillaume on 4/4/2017
  */
package predictiveModels.PMML

import java.io.FileInputStream

import com.typesafe.config.ConfigFactory
import org.dmg.pmml.{Model, PMML}
import org.jpmml.evaluator.{ModelEvaluator, ModelEvaluatorFactory}


object ModelLoader {

  val debug = ConfigFactory.load.getString("debug.flag").toBoolean

  /**/

  def load(path:String="./assets/LM_Model.pmml"):ModelEvaluator[_ <: Model] = {

    val modelLocation = new FileInputStream(path)
    val pmml: PMML = org.jpmml.model.PMMLUtil.unmarshal(modelLocation)

    val modelEvaluatorFactory = ModelEvaluatorFactory.newInstance
    val modelEvaluator = modelEvaluatorFactory.newModelEvaluator(pmml)
    println("INFO: loaded ..." + modelEvaluator.getSummary())
    println("INFO: input fields ..." + modelEvaluator.getInputFields())
    modelEvaluator
  }


}




  /*
  def loadTreeModel(p:PMML) :ModelEvaluator[TreeModel] = {
  val modelEvaluator = new TreeModelEvaluator(p)
    println("loaded ..." + modelEvaluator.getSummary())
    println("input fields ..." + modelEvaluator.getInputFields())
    modelEvaluator
  }




  def predict[T <: Model](evaluator:ModelEvaluator[T],pred:PredHash): Double = {

    val inputFields = evaluator.getInputFields()

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

    val results =  evaluator.evaluate(args)
    val targetFields = evaluator.getTargetFields
    val targetFieldValue = results.get(targetFields.head.getName).asInstanceOf[Double]

    println(targetFieldValue)
    targetFieldValue
  }

*/