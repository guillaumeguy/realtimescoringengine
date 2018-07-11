
/*
import play.api.ApplicationLoader.Context
import play.api.{Application, ApplicationLoader, Logger, LoggerConfigurator}

class AppLoader extends ApplicationLoader {

  override def load(context: Context): Application = {

    // Logger.configure(context.environment)

    LoggerConfigurator(context.environment.classLoader).foreach {_.configure(context.environment)}

    val components = new AppComponents(context)
    components.application
  }

}


*/
