import com.alanjz.meerkat.moves.PseudoLegalMover
import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.position.mutable.NodeStringBuilder

import scala.io.Source
import scala.util.Random

/**
 * Created by alan on 12/15/14.
 */
object RandomMoveTest extends App {
  val node = MaskNode.initialPosition
  val rand = new Random
  var moves = new PseudoLegalMover(node).getMoves

  while(moves.nonEmpty) {
    println(node)
    println(moves.mkString(" "))
    val line = Source.stdin.bufferedReader().readLine()
    if(line == "z") {
      node.unmake()
    }
    else {
      val move = moves(rand.nextInt(moves.size))
      println(node.active + " chose " + move + ".")
      node.make(move)
    }
    moves = new PseudoLegalMover(node).getMoves
  }
}
