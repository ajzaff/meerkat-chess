package com.alanjz.meerkat.util.basicEval

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.position.BasicNode

/**
 * Created by alan on 12/5/14.
 */
object PieceTableEvaluation extends Evaluation {

  val pawnTable = Array[Double](
    .00, .00, .00, .00, .00, .00, .00, .00,
    .50, .50, .50, .50, .50, .50, .50, .50,
    .10, .10, .20, .30, .30, .20, .10, .10,
    .05, .05, .10, .27, .27, .10, .05, .05,
    .00, .00, .00, .25, .25, .00, .00, .00,
    .05,-.05,-.10, .00, .00,-.10,-.05, .05,
    .05, .10, .10,-.25,-.25, .10, .10, .05,
    .00, .00, .00, .00, .00, .00, .00, .00
  )

  val KnightTable = Array[Double](
    -.50,-.40,-.30,-.30,-.30,-.30,-.40,-.50,
    -.40,-.20, .00, .00, .00, .00,-.20,-.40,
    -.30, .00, .10, .15, .15, .10, .00,-.30,
    -.30, .05, .15, .20, .20, .15, .05,-.30,
    -.30, .00, .15, .20, .20, .15, .00,-.30,
    -.30, .05, .10, .15, .15, .10, .05,-.30,
    -.40,-.20, .00, .05, .05, .00,-.20,-.40,
    -.50,-.40,-.20,-.30,-.30,-.20,-.40,-.50
  )

  val bishopTable = Array[Double](
    -.20,-.10,-.10,-.10,-.10,-.10,-.10,-.20,
    -.10, .00, .00, .00, .00, .00, .00,-.10,
    -.10, .00, .05, .10, .10, .05, .00,-.10,
    -.10, .05, .05, .10, .10, .05, .05,-.10,
    -.10, .00, .10, .10, .10, .10, .00,-.10,
    -.10, .10, .10, .10, .10, .10, .10,-.10,
    -.10, .05, .00, .00, .00, .00, .05,-.10,
    -.20,-.10,-.40,-.10,-.10,-.40,-.10,-.20
  )

  val kingTable = Array[Double](
    -.30,-.40,-.40,-.50,-.50,-.40,-.40,-.30,
    -.30,-.40,-.40,-.50,-.50,-.40,-.40,-.30,
    -.30,-.40,-.40,-.50,-.50,-.40,-.40,-.30,
    -.30,-.40,-.40,-.50,-.50,-.40,-.40,-.30,
    -.20,-.30,-.30,-.40,-.40,-.30,-.30,-.20,
    -.10,-.20,-.20,-.20,-.20,-.20,-.20,-.10,
     .20, .20, .00, .00, .00, .00, .20, .20,
     .20, .30, .10, .00, .00, .10, .30, .20
  )

  val kingEndgameTable = Array[Double](
    -.50,-.40,-.30,-.20,-.20,-.30,-.40,-.50,
    -.30,-.20,-.10, .00, .00,-.10,-.20,-.30,
    -.30,-.10, .20, .30, .30, .20,-.10,-.30,
    -.30,-.10, .30, .40, .40, .30,-.10,-.30,
    -.30,-.10, .30, .40, .40, .30,-.10,-.30,
    -.30,-.10, .20, .30, .30, .20,-.10,-.30,
    -.30,-.30, .00, .00, .00, .00,-.30,-.30,
    -.50,-.30,-.30,-.30,-.30,-.30,-.30,-.50
  )

  def evaluate(node : BasicNode): Double = {
    var score = 0.0
    val kt = if(EndgameFilter.isEndgame(node)) {
      kingEndgameTable
    }
    else {
      kingTable
    }
    for(i <- Square.A1.toInt to Square.H8.toInt) {
      node(Square(i)).orNull match {
        case Pawn(node.active) =>
          val s = node.active match {
            case White => pawnTable(63-i)
            case Black => pawnTable(i)
            case _ => 0.0
          }
          score += s
        case Knight(node.active) =>
          score += KnightTable(i)/*
        case Bishop(node.active) =>
          val s = node.active match {
            case White => bishopTable(63-i)
            case Black => bishopTable(i)
            case _ => 0.0
          }
          score += s
        case Bishop(node.active.other) =>
          val s = node.active.other match {
            case White => bishopTable(63-i)
            case Black => bishopTable(i)
            case _ => 0.0
          }
          score -= s
        case King(node.active) =>
          val s = node.active match {
            case White => kt(63-i)
            case Black => kt(i)
            case _ => 0.0
          }
          score += s
        case King(node.active.other) =>
          val s = node.active.other match {
            case White => kt(63-i)
            case Black => kt(i)
            case _ => 0.0
          }
          score -= s*/
        case _ =>
      }
    }
    score
  }
}