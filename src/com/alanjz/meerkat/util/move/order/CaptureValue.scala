package com.alanjz.meerkat.util.move.order

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move._
import com.alanjz.meerkat.util.basicEval.BasicEvaluation

object CaptureValue {
  def comparator(move : Move) : Double = move match {
    case m : PawnCapture =>
      -BasicEvaluation.pieceWeight(m.captured) + BasicEvaluation.pawnScore
    case m : KnightCapture =>
      -BasicEvaluation.pieceWeight(m.captured) + BasicEvaluation.knightScore
    case m : BishopCapture =>
      -BasicEvaluation.pieceWeight(m.captured) + BasicEvaluation.bishopScore
    case m : RookCapture =>
      -BasicEvaluation.pieceWeight(m.captured) + BasicEvaluation.rookScore
    case m : QueenCapture =>
      -BasicEvaluation.pieceWeight(m.captured) + BasicEvaluation.queenScore
    case m : KingCapture =>
      -BasicEvaluation.pieceWeight(m.captured) + BasicEvaluation.kingScore
    case _ => 0
  }
}
