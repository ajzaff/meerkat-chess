import com.alanjz.meerkat.moves.Move.{PawnPromoteCapture, PawnPromote}
import com.alanjz.meerkat.moves.{Move, PseudoLegalMover}
import com.alanjz.meerkat.pieces.Color
import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.position.mutable.MaskNode

import scala.io.Source
import scala.util.Random

/**
 * Created by alan on 12/16/14.
 */
object PlayRandomGameTest extends App {
  print("Human plays (w)hite or (b)lack:")
  val who = Source.stdin.bufferedReader().readLine()
  val human = who match {
    case "w" => White
    case "b" => Black
    case _ => White
  }
  println("defaulted to human white.")
  var active : Color = White
  val pos = MaskNode.initialPosition
  var hasMoves = true
  var check = false
  while(hasMoves) {
    val legalMoves = new PseudoLegalMover(pos).getMoves.filter(
      m => {
        pos.make(m)
        val legal = !pos.isTerminal
        pos.unmake()
        legal
      })
    println(pos)
    if(active == human && legalMoves.nonEmpty) {
      print(":")
      var move : Option[Move] = None
      while(move == None) {
        val moveString = Source.stdin.bufferedReader().readLine()
        move = legalMoves.find(
          m => m match {
            case PawnPromote(o, t, p) => s"$o$t${p.toChar.toUpper}" == moveString
            case PawnPromoteCapture(o, t, _, p) => s"$o$t${p.toChar.toUpper}" == moveString
            case _ => s"${m.origin}${m.target}" == moveString
          }
        )
      }
      pos.make(move.get)
    }
    else if(legalMoves.nonEmpty) {
      val move = legalMoves(Random.nextInt(legalMoves.size))
      println(s"I choose ${move.origin}${move.target}.")
      pos.make(move)
    }

    // Update
    hasMoves = legalMoves.nonEmpty
    if(hasMoves) active = !active
  }

  if(!hasMoves && active == human) {
    println("It seems you have no legal moves.")
  }
  else {
    println("It seems I have no legal moves.")
  }
}
