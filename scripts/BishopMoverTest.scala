import com.alanjz.meerkat.moves.BishopMover
import com.alanjz.meerkat.pieces.Color.Black
import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.position.mutable.{FENMaskNodeBuilder, NodeStringBuilder}

/**
 * Created by alan on 12/15/14.
 */
object BishopMoverTest extends App {
  /*
  MaskNode.initialPosition
FENMaskNodeBuilder.parse("8/8/8/8/8/8/8/B7 w - - 0 1")
FENMaskNodeBuilder.parse("8/8/8/8/3B4/8/8/B7 w - - 0 1")
8/8/8/2n1n3/3B4/2n1n3/8/8 w - - 0 1
8/8/8/2N1N3/3B4/2N1N3/8/8 w - - 0 1

  MaskNode.initialPosition
FENMaskNodeBuilder.parse("8/8/8/8/8/8/8/b7 b - - 0 1")
FENMaskNodeBuilder.parse("8/8/8/8/3b4/8/8/b7 b - - 0 1")
8/8/8/2N1N3/3b4/2N1N3/8/8 b - - 0 1
8/8/8/2n1n3/3b4/2n1n3/8/8 b - - 0 1
   */
  val pos = FENMaskNodeBuilder.parse("8/8/8/2n1n3/3B4/2n1n3/8/8 w - - 0 1")

  println( NodeStringBuilder.mkString(pos) )

  val moves = new BishopMover(pos).mkList

  println("moves: " + moves.mkString(" "))
}
