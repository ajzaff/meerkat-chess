package com.alanjz.meerkat.util.basicEval

import com.alanjz.meerkat.pieces.Piece
import com.alanjz.meerkat.pieces.Piece.{King, Queen}
import com.alanjz.meerkat.position.BasicNode

/**
 * Created by alan on 12/5/14.
 */
object EndgameFilter extends Evaluation {

  val endgameThreshold = 14

  def isEndgame(node : BasicNode) : Boolean = {
    val iter = node.pieces.iterator
    var score = 0d
    while(iter.hasNext) {
      val piece = iter.next().orNull
      piece match {
        case Queen(node.active.other) => return false
        case King(_) =>
        case null =>
        case p : Piece => if(p.color == node.active) {
          score += MaterialEvaluation.pieceWeight(p)
        }
      }
      if(score > endgameThreshold) {
        return false
      }
    }
    true
  }
}
