// ------------------------------ //
// ------------------------------ //
// ------------------------------ //

trait Model{ def predict: Int  }

case class Model1(i:Int) extends Model{    def predict: Int = 1}

case class Model2(i:Int) extends Model {
  def predict: Int = 2
}

case object a {

val a1 = new Model1(1)
val a2 = new Model2(2)

a1.predict
a2.predict

}

// ------------------------------ //
// ------------------------------ //
// ------------------------------ //
