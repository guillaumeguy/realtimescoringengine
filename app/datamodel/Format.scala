package datamodel

object Format {

  // Send to the database
  case class Keys(ID:Int)

  // Gotten back from the database
  case class DBLayoutFormat(
                                 ID: Int,
                                 //     SPEND_12M: Double,
                                 RECENCY: Double,
                                 LAST_EMAIL_OPEN: Double,
                                 HOUSEHOLD_INCOME: Double,
                                 CHANNEL_ACQUISITION: String
                               ) {


    def toMergedFormat: MergedFormat = {

      new MergedFormat(
        ID = this.ID,
        //   SPEND_12M = this.SPEND_12M,
        RECENCY = this.RECENCY,
        LAST_EMAIL_OPEN = this.LAST_EMAIL_OPEN,
        HOUSEHOLD_INCOME = this.HOUSEHOLD_INCOME,
        CHANNEL_ACQUISITION = this.CHANNEL_ACQUISITION.toUpperCase
      )
    }
  }


    case class MergedFormat(
                                 ID: Int,
                            //     SPEND_12M: Double,
                                 RECENCY: Double,
                                 LAST_EMAIL_OPEN: Double,
                                 HOUSEHOLD_INCOME: Double,
                                 CHANNEL_ACQUISITION: String
                               ) {

    def toPredFormat: PredFormat = {

      new PredFormat(
        ID = this.ID,
        //   SPEND_12M = this.SPEND_12M,
        RECENCY = this.RECENCY,
        LAST_EMAIL_OPEN = this.LAST_EMAIL_OPEN,
        HOUSEHOLD_INCOME = this.HOUSEHOLD_INCOME,
        CHANNEL_ACQUISITION = this.CHANNEL_ACQUISITION.toUpperCase.replaceAll("\\s", "") match {
          case "DIRECT" => 0
          case "RETAIL" => 1
          case "ONLINE" => 1
          case "REFERRAL" => 2
          case _ => throw new Exception("Unknown Channel ..")
        }
      )
    }
  }



  case class PredFormat(
                         ID: Int,
       //                   SPEND_12M: Double,
                         RECENCY: Double,
                         LAST_EMAIL_OPEN: Double,
                         HOUSEHOLD_INCOME: Double,
                         CHANNEL_ACQUISITION: Int
                       ) {


    def toMap: Map[String, Any] =
      this.getClass.getDeclaredFields.foldLeft(Map[String, Any]()) {
        (a, f) =>
          f.setAccessible(true)
          a + (f.getName -> f.get(this))
      }
  }


  case class RuleInputFormat( ID: Int,
                              //                   SPEND_12M: Double,
                              RECENCY: Double,
                              LAST_EMAIL_OPEN: Double,
                              HOUSEHOLD_INCOME: Double,
                              CHANNEL_ACQUISITION: Int
                              )

  type PredHash = Map[String,Any]


}


/*
val dummy  = new PredFormat(1,12.0,12.0,12.0,12.0,2)
dummy.toMap
dummy.toMap2
*/
