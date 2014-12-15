import com.alanjz.meerkat.moves.KnightMover
import com.alanjz.meerkat.pieces.Color.Black
import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.position.mutable.{NodeStringBuilder, FENMaskNodeBuilder}

/**
 * Created by alan on 12/15/14.
 */
object KnightMoverTest extends App {
  val pos = MaskNode.initialPosition

  println( NodeStringBuilder.mkString(pos) )

  val moves = new KnightMover(pos).mkList

  println("moves: " + moves.mkString(" "))
}
