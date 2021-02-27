//standard imports to use stainless verification
import stainless.lang._
import stainless.collection._
import stainless.annotation._
import stainless.math.Nat

//module in which the data / methods are verified
//NB! In stainless, you have to write in "Pure Scala" : https://epfl-lara.github.io/stainless/purescala.html
object verified {
  //Value class
  case class Natural(value : BigInt) extends AnyVal {
    @invariant
    def isPositive : Boolean = value >= 0

    def next : Natural = { Natural(value + 1) } ensuring (res => res.value > 0)
  }
}

