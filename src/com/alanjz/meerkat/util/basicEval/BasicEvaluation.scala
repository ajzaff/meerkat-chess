package com.alanjz.meerkat.util.basicEval

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.basicMove.CheckChecker

object BasicEvaluation extends Evaluation {

  val pawnScore = 1.0
  val knightScore = 3.0
  val bishopScore = 3.2
  val rookScore = 5.0
  val queenScore = 9.0
  val kingScore = 84.0

  val checkmate = -99
  val stalemate = 0
  val fiftyMove = 0

  val score = Map[Piece, Double](
    Pawn(White) -> pawnScore,
    Pawn(Black) -> -pawnScore,
    Knight(White) -> knightScore,
    Knight(Black) -> -knightScore,
    Bishop(White) -> bishopScore,
    Bishop(Black) -> -bishopScore,
    Rook(White) -> rookScore,
    Rook(Black) -> -rookScore,
    Queen(White) -> queenScore,
    Queen(Black) -> -queenScore,
    King(White) -> kingScore,
    King(Black) -> -kingScore
  )

  val pieceWeight = Map[Piece, Double](
    Pawn(White) -> pawnScore,
    Pawn(Black) -> pawnScore,
    Knight(White) -> knightScore,
    Knight(Black) -> knightScore,
    Bishop(White) -> bishopScore,
    Bishop(Black) -> bishopScore,
    Rook(White) -> rookScore,
    Rook(Black) -> rookScore,
    Queen(White) -> queenScore,
    Queen(Black) -> queenScore,
    King(White) -> kingScore,
    King(Black) -> kingScore
  )

  def evaluate(node : BasicNode) : Double = {
    if(node.halfMove >= 50) {
      return fiftyMove * node.active.toInt
    }
    else {
      val inCheck = new CheckChecker(node).inCheck
      val eval =
        if(node.moves.isEmpty && inCheck) {
          checkmate
        }
        else if(node.moves.isEmpty) {
          stalemate
        }
        else {
          node.pieces.map(p =>
            if (p.isDefined) score(p.get)
            else 0).sum
        }
      (eval * 100).round / 100.0 * node.active.toInt
    }
  }
}
