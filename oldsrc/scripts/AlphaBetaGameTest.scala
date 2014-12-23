import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move.{PawnPromote, PawnPromoteCapture}
import com.alanjz.meerkat.pieces.Color
import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.position.immutable.BasicNode
import com.alanjz.meerkat.search.AlphaBetaSearch
import com.alanjz.meerkat.util.basicMove.MoveGenerator
import com.alanjz.meerkat.util.position.immutable.FENParser

import scala.io.Source

object AlphaBetaGameTest extends App {
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
//BasicNode.startingPosition //

  /*
   * The position given by:
   *
   * k1r2b2/p4p1p/N1Np1q2/2pQ2p1/1P2PP1r/P3B3/2P3PP/R4RK1 b - - 0 1
   *
   * I think causes a bug in Quiescence.
   */

  var node = BasicNode.startingPosition//FENParser.parse("5q2/2k5/2n2pQ1/4p3/RP2P3/2P2R1P/2P1K2P/8 b - - 0 1")//BasicNode.startingPosition//FENParser.parse("k1r2b2/p4p1p/N1Np1q2/2pQ2p1/1P2PP1r/P3B3/2P3PP/R4RK1 b - - 0 1")
  var moves : List[Move] = Nil
  do {
    moves = new MoveGenerator(node).generateAll
    val color = node.active.toString
    val ms = moves.mkString(",")
    if(!moves.isEmpty) {
      var move : Move = null
      if(node.active == human) {
        do {
          print("move (ex: a3c4): ")
          val moveString = Source.stdin.bufferedReader().readLine()
          move = moves.find(
            m => m match {
              case PawnPromote(_,_,p) => s"${m.origin}${m.target}${p.toChar.toUpper}" == moveString
              case PawnPromoteCapture(_,_,_,p) => s"${m.origin}${m.target}${p.toChar.toUpper}" == moveString
              case _ => s"${m.origin}${m.target}" == moveString
            }
          ).orNull
          if(move == null) {
            println("illegal move.")
          }
        }
        while(move == null)
        node = node.move(move)
      }
      else {
        val best = new AlphaBetaSearch(3).search(node)
        move = best._1
        println(s"$move is chosen [${best._2}].")
        node = node.move(move)
        println(node.verboseString)
        println
      }
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