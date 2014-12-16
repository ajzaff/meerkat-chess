import com.alanjz.meerkat.moves.{QueenMover, PseudoLegalMover}
import com.alanjz.meerkat.util.position.mutable.FENMaskNodeBuilder

/**
 * Created by alan on 12/15/14.
 */
object PositionTest extends App {
  val pos = FENMaskNodeBuilder.parse("rnbqkbnr/1ppppppp/8/p7/7P/5P2/PPPPP1P1/RNBQQBNR w - - 0 1")
  val moves = new QueenMover(pos).mkList //new PseudoLegalMover(pos).getMoves
  //val move = moves.find(_.toString == "b5").get
  println(pos)
  //pos.make(move)
  //println(pos)
  println(moves.mkString(" "))
}
