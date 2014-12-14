import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.util.basicMove.MoveGenerator
import com.alanjz.meerkat.util.move.order.CaptureValue
import com.alanjz.meerkat.util.position.immutable.FENParser

object CaptureValueTest extends App {
  val node = FENParser.parse("8/8/1k6/1P3bpp/4BQRN/8/8/8 b - - 0 1")
  val moves = new MoveGenerator(node).generateAll.sortBy(CaptureValue.comparator)
  val captures = new MoveGenerator(node).generateAll.filter(m => m.isInstanceOf[Move.Capture])
  println(node.verboseString)
  println(moves.mkString(" "))
  println(captures.mkString(" "))

  //println (BasicEvaluation.evaluate(node))
  //println (BasicEvaluation.evaluate(node as Black))
}
