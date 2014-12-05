import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.basicMove.MoveGenerator

import scala.util.Random

object RandomGameTest extends App {
  var node = BasicNode.startingPosition
  var moves : List[Move] = Nil
  do {
    moves = new MoveGenerator(node).generateAll
    val color = node.active.toString
    val ms = moves.mkString(",")
    if(!moves.isEmpty) {
      //println(s"$color: $ms")
      val move = moves(Random.nextInt(moves.size))
      //println(s"$move is chosen.")
      node = node.move(move)
      //println(node.verboseString)
      //println
      //Source.stdin.bufferedReader().readLine()
    }
    else {
      println(node.verboseString)
      println(s"$color has no moves.")
    }
  }
  while(! moves.isEmpty && node.halfMove < 50)

  if(node.halfMove >= 50) {
    println(node.verboseString)
    println(s"50 move rule called.")
  }
}
