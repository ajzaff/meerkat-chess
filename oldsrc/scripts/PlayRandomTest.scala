import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces.Color
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.position.immutable.BasicNode
import com.alanjz.meerkat.util.basicMove.MoveGenerator

import scala.io.Source
import scala.util.Random

object PlayRandomTest extends App {
  print("Human (w)hite or (b)lack?: ")
  val line = Source.stdin.bufferedReader().readLine()
  val human : Color =
    if(line == "w") {
      println("Human playing white")
      White
    }
    else if(line == "b") {
      println("Human playing black")
      Black
    }
    else {
      println("aborting.")
      System.exit(1)
      Black
    }

  var node = BasicNode.startingPosition
  var moves : List[Move] = Nil
  do {
    moves = new MoveGenerator(node).generateAll
    val color = node.active.toString
    val ms = moves.mkString(",")
    if(!moves.isEmpty) {
      println(s"$color: $ms")

      var move : Move = null
      if(node.active == human) {
        do {
          print("move (ex: a3c4): ")
          val moveString = Source.stdin.bufferedReader().readLine()
          move = moves.find(m => s"${m.origin}${m.target}" == moveString).getOrElse(null)
          if(move == null) {
            println("illegal move.")
          }
        }
        while(move == null)
      }
      else {
        move = moves(Random.nextInt(moves.size))
        println(s"$move is chosen.")
      }
      node = node.move(move)
      println(node.verboseString)
      println
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
