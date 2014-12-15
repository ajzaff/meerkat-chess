import com.alanjz.meerkat.moves.PawnMover
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.position.mutable.{NodeStringBuilder, FENMaskNodeBuilder}

/**
 * Created by alan on 12/15/14.
 */
object PawnMoverTest extends App {
  /*
  rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
  8/8/8/3nnn2/4P3/8/8/8 w KQkq - 0 1
  8/8/8/3Pp3/8/8/8/8 w KQkq e6 0 1
  8/8/8/3PpP2/8/8/8/8 w KQkq e6 0 1
  nnn1nnnn/3P4/8/8/8/8/8/8 w KQkq - 0 1


  rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1
  8/8/8/4p3/3NNN2/8/8/8 b KQkq - 0 1
  8/8/8/8/3Pp3/8/8/8 b KQkq d3 0 1
  8/8/8/8/3pPp2/8/8/8 b KQkq e3 0 1
  8/8/8/8/8/8/3p4/NNN1NNNN b KQkq - 0 1
   */


  val pos = FENMaskNodeBuilder.parse("8/8/8/8/8/8/3p4/NNN1NNNN b KQkq - 0 1")

  println( NodeStringBuilder.mkString(pos) )

  val moves = new PawnMover(pos).mkList

  println("moves: " + moves.mkString(" "))
}
