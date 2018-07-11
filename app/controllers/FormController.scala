

package controllers

import javax.inject._

import com.typesafe.config.ConfigFactory
import connectors.DBConnector
import datamodel.Format.Keys
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{DefaultLangs, DefaultMessagesApi, I18nSupport, MessagesApi}
import play.api.mvc._
import play.db.NamedDatabase
// import play.api.i18n.Messages.Implicits._
import play.api.data.format.Formats._


import scala.collection.mutable.ArrayBuffer

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */


case class Customer(name:String, key:Int, scoreValue:Double){

  val scoreValueFormatted:String =  f"${this.scoreValue}%1.2f"
}
case class CustomerWithoutScore(name:String, key:Int)  {
  def toCustomer(score:Double): Customer = new Customer(this.name,this.key,scoreValue = score)
  }


/*
val messagesApi: MessagesApi = new DefaultMessagesApi(environment, configuration, new DefaultLangs(configuration))
  val FormControllerController = new FormController(messagesApi)(csrfConfig)
 */


// @Singleton
class FormController  @Inject()(val messagesApi: MessagesApi
       , @NamedDatabase("default") db: play.api.db.Database
      ,model:predictiveModels.PMML.ModelScorerTrait

                               ) //   csrfConfig: CSRFConfig)
  extends Controller  with I18nSupport
// with CSRFComponents
 {


  val debug = ConfigFactory.load.getString("debug.flag").toBoolean

  if(debug) println("debug mode on")

   private val customers = ArrayBuffer(
    Customer("Customer1", 1,12.0),
    Customer("Customer2", 2,12.0),
    Customer("Customer3", 3,13.0)
  )


   def index = Action {
     Ok(views.html.index("Your new application is ready."))
   }

   def listCustomer = Action { implicit request =>
     // Pass an unpopulated form to the template
     Ok(views.html.listCustomer(customers, FormController.createCustomerForm))
   }

  // This will be the action that handles our form post
  def createCustomer = Action { implicit request =>
    val formValidationResult = FormController.createCustomerForm.bindFromRequest

    // formValidationResult.value

    formValidationResult.fold({ formWithErrors =>
      // This is the bad case, where the form had validation errors.
      // Let's show the user the form again, with the errors highlighted.
      // Note how we pass the form with errors to the template.
      if(debug) println("BUG ! ")
      if(debug) println( formWithErrors.errors)


      BadRequest(views.html.listCustomer.apply(customers, formWithErrors))
      // BadRequest(views.html.listCustomer.apply(customers, formWithErrors))
    }, { customer =>
      // This is the good case, where the form was successfully parsed as a Customer.

      val k = Keys(customer.key)

      val preds =  DBConnector.getPredictors(db,k) match {
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

      customers.append(customer.toCustomer(customerValue))
      Redirect(routes.FormController.listCustomer)
    })
  }

}

object FormController {

  /** The form definition for the "create a widget" form.
    *  It specifies the form fields and their types,
    *  as well as how to convert from a Widget to form data and vice versa.
    */
  val createCustomerForm2 = Form(
    mapping(
      "name" -> text
      ,"key" -> number
      , "scoreValue" -> of(doubleFormat)
    )(Customer.apply)(Customer.unapply) )

  val createCustomerForm = Form(
    mapping(
      "name" -> text
      ,"key" -> number
    )(CustomerWithoutScore.apply)(CustomerWithoutScore.unapply)
  )


}