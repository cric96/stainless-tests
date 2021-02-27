
object Main {
  //Here you can use verified functions, in "full" scala language.
  import verified._

  def main(args: Array[String]): Unit = {
    //You can use verified code seamlessly with "unsafe" code
    val nat = new Natural(0)
    println(nat.next)
  }

}

