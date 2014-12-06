package com.alanjz.meerkat.util.move.order

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move._
import com.alanjz.meerkat.util.basicEval.{MaterialEvaluation, BasicEvaluation}

object CaptureValue {
  def comparator(move : Move) : Double = move match {
    case m : PawnCapture =>
      -5 - MaterialEvaluation.pieceWeight(m.captured)
    case m : KnightCapture =>
      -3 - MaterialEvaluation.pieceWeight(m.captured)
    case m : BishopCapture =>
      -3 - MaterialEvaluation.pieceWeight(m.captured)
    case m : RookCapture =>
      -2 - MaterialEvaluation.pieceWeight(m.captured)
    case m : QueenCapture =>
      -1 - MaterialEvaluation.pieceWeight(m.captured)
    case m : KingCapture =>
      -MaterialEvaluation.pieceWeight(m.captured)
    case _ => 0
  }
}
