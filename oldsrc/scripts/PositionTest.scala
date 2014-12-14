import com.alanjz.meerkat.pieces.Color.Black
import com.alanjz.meerkat.position.immutable.BasicNode
import com.alanjz.meerkat.util.basicMove.MoveGenerator

object PositionTest extends App {
  var node = BasicNode.startingPosition
  println(node)
  //println(BasicEvaluation.evaluate(node))
  println("Moves:")
  val whiteMoves = new MoveGenerator(node).generateAll
  val blackMoves = new MoveGenerator(node as Black).generateAll
  println(whiteMoves.mkString(","))
  println(blackMoves.mkString(","))
  node = node.move(whiteMoves.head)
  println(node)
  node = node.move(blackMoves(5))
  println(node)
}