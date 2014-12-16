import com.alanjz.meerkat.moves.QueenMover
import com.alanjz.meerkat.util.position.mutable.{NodeStringBuilder, FENMaskNodeBuilder}

/**
 * Created by alan on 12/15/14.
 */
object QueenMoverTest extends App {

  /*
8/8/2NNN3/2NQN3/2NNN3/8/8/8 w - - 0 1
8/8/2nnn3/2nQn3/2nnn3/8/8/8 w - - 0 1
8/8/8/3Q4/8/8/8/8 w - - 0 1
8/8/8/8/8/8/6N1/6NQ w - - 0 1

8/8/2nnn3/2nqn3/2nnn3/8/8/8 b - - 0 1
8/8/2NNN3/2NqN3/2NNN3/8/8/8 b - - 0 1
8/8/8/3q4/8/8/8/8 b - - 0 1
8/8/8/8/8/8/6n1/6nq b - - 0 1
 */

  val pos = FENMaskNodeBuilder.parse("8/8/8/8/8/8/6n1/6nq b - - 0 1")

  println( NodeStringBuilder.mkString(pos) )

  val moves = new QueenMover(pos).mkList

  println("moves: " + moves.mkString(" "))
}
