package com.alanjz.meerkat.util.basicEval

import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece.Pawn
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.Square

/**
 * Created by alan on 12/5/14.
 */
object PawnEvaluation extends Evaluation {

  val matrix_white = Array[Double](
    0,0,0,0,0,0,0,0,
    0.05,  0.1,  0.1, -0.25,  -0.25,  0.1,  0.1,  0.05,
    0.05, -0.05, -0.10, 0, 0, -0.1, -0.05, 0.05,
    0, 0, 0, 0.25, 0.25, 0, 0, 0,
    0.05, 0.05, 0.1, 0.27, 0.27, 0.1, 0.05, 0.05,
    0.1, 0.1, 0.2, 0.3, 0.3, 0.2, 0.1, 0.1,
    0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
    0,0,0,0,0,0,0,0
  )

  val matrix_black = Array[Double](
    0,0,0,0,0,0,0,0,
    0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5,
    0.1, 0.1, 0.2, 0.3, 0.3, 0.2, 0.1, 0.1,
    0.05, 0.05, 0.1, 0.27, 0.27, 0.1, 0.05, 0.05,
    0, 0, 0, 0.25, 0.25, 0, 0, 0,
    0.05, -0.05, -0.1, 0, 0, -0.1, -0.05, 0.05,
    0.05, 0.1, 0.1, -0.25, -0.25, 0.1, 0.1, 0.05,
    0,0,0,0,0,0,0,0
  )

  def evaluate(node : BasicNode): Double = {
    val matrix = node.active match {
      case White => matrix_white
      case Black => matrix_black
    }
    var score = 0.0
    for(i <- Square.A1.toInt to Square.H8.toInt) {
      val piece = node(Square(i)).orNull

      piece match {
        case Pawn(node.active) => score += matrix(i)
        case _ =>
      }
    }

    score
  }
}
