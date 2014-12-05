package com.alanjz.meerkat.util.move.order

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move._
import com.alanjz.meerkat.util.basicEval.BasicEvaluation

object CaptureValue {
  def comparator(move : Move) : Double = move match {
    case m : PawnCapture =>
      5 + BasicEvaluation.pieceWeight(m.captured)
    case m : KnightCapture =>
      3 + BasicEvaluation.pieceWeight(m.captured)
    case m : BishopCapture =>
      3 + BasicEvaluation.pieceWeight(m.captured)
    case m : RookCapture =>
      2 + BasicEvaluation.pieceWeight(m.captured)
    case m : QueenCapture =>
      1 + BasicEvaluation.pieceWeight(m.captured)
    case m : KingCapture =>
      BasicEvaluation.pieceWeight(m.captured)
    case _ => 0
  }
}
