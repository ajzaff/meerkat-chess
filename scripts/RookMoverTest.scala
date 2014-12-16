import com.alanjz.meerkat.moves.RookMover
import com.alanjz.meerkat.util.position.mutable.{NodeStringBuilder, FENMaskNodeBuilder}

/**
 * Created by alan on 12/15/14.
 */
object RookMoverTest extends App {

  /*
8/8/3N4/2NRN3/3N4/8/8/8 w - - 0 1
8/8/3n4/2nRn3/3n4/8/8/8 w - - 0 1
8/8/8/3R4/8/8/8/8 w - - 0 1
8/8/8/8/8/8/8/6NR w - - 0 1

8/8/3n4/2nrn3/3n4/8/8/8 b - - 0 1
8/8/3N4/2NrN3/3N4/8/8/8 b - - 0 1
8/8/8/3r4/8/8/8/8 b - - 0 1
8/8/8/8/8/8/8/6nr b - - 0 1
   */

  val pos = FENMaskNodeBuilder.parse("8/8/8/8/8/8/8/6nr b - - 0 1")

  println( NodeStringBuilder.mkString(pos) )

  val moves = new RookMover(pos).mkList

  println("moves: " + moves.mkString(" "))
}
