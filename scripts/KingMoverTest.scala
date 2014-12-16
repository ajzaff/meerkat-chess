import com.alanjz.meerkat.moves.KingMover
import com.alanjz.meerkat.util.position.mutable.{NodeStringBuilder, FENMaskNodeBuilder}

/**
 * Created by alan on 12/15/14.
 */
object KingMoverTest extends App {

 /*
 8/8/8/3K4/8/8/8/8 w - - 0 1
 8/8/2NNN3/2NKN3/2NNN3/8/8/8 w - - 0 1
 8/8/2nnn3/2nKn3/2nnn3/8/8/8 w - - 0 1
 8/6K1/8/8/8/8/8/8 w - - 0 1
 8/8/8/8/8/8/8/R3K2R w KQ - 0 1

 8/8/8/3k4/8/8/8/8 b - - 0 1
 8/8/2nnn3/2nkn3/2nnn3/8/8/8 b - - 0 1
 8/8/2NNN3/2NkN3/2NNN3/8/8/8 b - - 0 1
 8/6k1/8/8/8/8/8/8 b - - 0 1
 r3k2r/8/8/8/8/8/8/8 b kq - 0 1
  */

 val pos = FENMaskNodeBuilder.parse("r3k2r/8/8/8/8/8/8/8 b kq - 0 1")

  println( NodeStringBuilder.mkString(pos) )

  val moves = new KingMover(pos).mkList

  println("moves: " + moves.mkString(" "))
}
