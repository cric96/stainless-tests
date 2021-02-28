// Start writing your ScalaFiddle code here
//standard imports to use stainless verification
import stainless.lang._
import stainless.collection._
import stainless.proof._
object verified {
  private def _nextCut(list : List[BigInt], position : BigInt, condition : Boolean) : BigInt = {
    require(position > 0 && list.nonEmpty)
    list match {
      case head :: other :: rest if (head >= other == condition) => _nextCut(other :: rest, position + 1, condition)
      case head :: rest => position
      case rest => position
    }
  }.ensuring(res => res > 0)
  
  def nextCut(list : List[BigInt], condition : Boolean) : BigInt = {
  	require(list.nonEmpty)
    _nextCut(list, BigInt(1), condition) //one element is already considered
  }.ensuring(res => res > 0)

  private def _cut(list : List[BigInt], startIndex : BigInt) : List[BigInt] = {
    require(startIndex >= 0)
    decreases(list.size)
    val res : List[BigInt] = list match {
       case head :: other :: rest =>
        val nextCutRelativePosition = nextCut(other :: rest, head >= other)
         
         val lastIndex = startIndex + nextCutRelativePosition
         val tail : List[BigInt] = (other :: rest) drop (nextCutRelativePosition)
         //val tail = _cut(elements, lastIndex + 1)
         (lastIndex + 1) :: _cut(tail, lastIndex + 1)
      case head :: Nil() => List(startIndex + 1)
      case rest => Nil()
    }
    res
  }.ensuring(res => res.forall(_ >= 0))

  def cut(list : List[BigInt]) : List[BigInt] = {
  	require(list.nonEmpty)
  	BigInt(0) :: _cut(list, 0)
  } ensuring (res => {
  	val n = list.size
  	res.nonEmpty && //non empty properties
  	res.head == 0 && // res.reverse.head == n ! doesn't work (prop : begin-to-end)
  	res.forall(_ >= 0) // res.forall(_ <= n) ! doesn't work (prop : within bounds:)
  	//monotic ?? how can we prove it?
  })
}