
/**
  * Created by Guy Guillaume on 4/5/2017.
  * Copyright Guillaume Guy
  */


package connectors

import datamodel.Format._
import play.api.Play
import play.api.db.Database
import anorm._



object DBConnector {

val verbose = true

  def getPredictors(db:Database,id:Keys,tableName:String="predictors"):Option[DBLayoutFormat] = {

    implicit val conn = db.getConnection() // "default")

    val n = classOf[DBLayoutFormat].getDeclaredFields.map(_.getName).mkString(", ")
    if(verbose) println(f"fetching columns: $n")

    // This MACRO will generate dynamically
    val parser: RowParser[DBLayoutFormat] = Macro.namedParser[DBLayoutFormat]
    val s = SQL"""SELECT #$n FROM #$tableName where ID = #${id.ID}"""
    val result: List[DBLayoutFormat] = s.as[List[DBLayoutFormat]](parser.*)
    // val rs = stmt.executeQuery("select * from tableName WHERE ID = ${id.ID}")
    conn.close()
    if(verbose) println(result)


    result match {
      case Nil => None
      case a => Some(a.head)
    }
  }


  // val db = Database.forConfig("db")
//  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
/*

  class predictorsTableDef(tag: Tag) extends Table[DBLayoutFormat](tag, "user") {

    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def firstName = column[String]("first_name")
    def lastName = column[String]("last_name")
    def mobile = column[Long]("mobile")
    def email = column[String]("email")


    def id =  column[String]("customerID",O.PrimaryKey),
    def recency = column[Double]("RECENCY"),
    def lastEmailOpen = column[Double]("LAST_EMAIL_OPEN"),
    def householdIncome = column[Double]("HOUSEHOLD_INCOME"),

    override def * =  (id, recency, lastEmailOpen, householdIncome )
  }
  */
}
