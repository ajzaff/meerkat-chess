package com.alanjz.meerkat.util.basicEval

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.basicMove.CheckChecker

object BasicEvaluation extends Evaluation {

  val checkmate = -99
  val stalemate = 0
  val fiftyMove = 0

  def evaluate(node : BasicNode) : Double = {
    if(node.halfMove >= 50) {
      return fiftyMove * node.active.toInt
    }
    else {
      val inCheck = new CheckChecker(node).inCheck

      if(node.moves.isEmpty && inCheck) {
        return checkmate * node.active.toInt
      }

      else if(node.moves.isEmpty) {
        return stalemate * node.active.toInt
      }

      var eval = MaterialEvaluation.evaluate(node)
      eval += PieceTableEvaluation.evaluate(node)
      eval += CastlingFilter.evaluate(node)

      eval * node.active.toInt
    }
  }
}
