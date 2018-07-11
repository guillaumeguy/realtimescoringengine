
/*
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import controllers.{AsyncController, FormController, ScoringControllerDB}
import play.api.ApplicationLoader.Context
import play.api.{BuiltInComponentsFromContext, db}
import play.api.db.{DB, DefaultDBApi}
import play.api.i18n.{DefaultLangs, DefaultMessagesApi, MessagesApi}
import play.api.routing.Router
import play.api.http.HttpFilters
import play.db.Database
import play.filters.csrf._
import play.filters.csrf.CSRF.ConfigTokenProvider
import predictiveModels.PMML.ModelScorerTrait
import router.Routes


class AppComponents(context: Context) extends BuiltInComponentsFromContext(context) with CSRFComponents {

  override lazy val httpFilters = Seq(csrfFilter)
  val messagesApi: MessagesApi = new DefaultMessagesApi(environment, configuration, new DefaultLangs(configuration))
  val FormControllerController = new FormController(messagesApi)(csrfConfig)

  // val a = new DefaultDBApi(configuration).database("default")
 // val a = DB.getDataSource("default").
   val pmmlModel :ModelScorerTrait = new predictiveModels.PMML.ModelScorer()

  val db1 = play.api.db.Databases(
      driver = ConfigFactory.load.getString("db.default.driver")
    , url = ConfigFactory.load.getString("db.default.url")
    ,config = Map("username" -> ConfigFactory.load.getString("db.default.username")
    , "password" -> ConfigFactory.load.getString("db.default.password") )
  )


  val ScoringControllerDBController = new ScoringControllerDB( db = db1 , model = pmmlModel)

  //val aController = new AsyncController( new ActorSystem("temp") )

  val assets = new controllers.Assets(httpErrorHandler)
  val router: Router = new Routes(httpErrorHandler
        , ScoringControllerDBController
    , FormControllerController, assets,prefix="")
 //  override lazy val router = new Routes(httpErrorHandler, applicationController, assets)

}

*/
