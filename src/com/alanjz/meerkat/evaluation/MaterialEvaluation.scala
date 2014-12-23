package com.alanjz.meerkat.evaluation

import com.alanjz.meerkat.position.mutable.MaskNode

object MaterialEvaluation {
  val pawnScore = 1.0
  val knightScore = 3.0
  val bishopScore = 3.2
  val rookScore = 5.0
  val queenScore = 9.0

  def evaluate(node : MaskNode) : Double = {
    val whiteScore =
      node.whitePawns * pawnScore +
      node.whiteKnights * knightScore +
      node.whiteBishops * bishopScore +
      node.whiteRooks * rookScore +
      node.whiteQueens * queenScore
    val blackScore =
      node.blackPawns * pawnScore +
      node.blackKnights * knightScore +
      node.blackBishops * bishopScore +
      node.blackRooks * rookScore +
      node.blackQueens * queenScore
    (whiteScore - blackScore) * node.active.toInt
  }
}