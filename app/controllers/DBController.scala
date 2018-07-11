
/**
  * Created by Guy Guillaume on 4/5/2017.
  * Copyright Guillaume Guy
  */


package controllers

import javax.inject.Inject

import play.api.Play.current
import play.api.mvc._
import play.api.db._


class DBController @Inject()(db: Database)  extends Controller {


  def index = Action {
    var outString = "Number is "

    val conn = db.getConnection() // "default")

    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT count(*) as testkey from predictors")

      while (rs.next()) {
        outString += rs.getString("testkey")
      }
    } finally {
      conn.close()
    }
    Ok(outString)
  }

/*
  def index(name: String) = Action.async { implicit request =>
    val resultingUsers: Future[Seq[User]] = dbConfig.db.run(
      Users.filter(_.name === name).result)
    resultingUsers.map(users => Ok(views.html.index(users)))
  }
  */
}
