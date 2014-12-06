package com.alanjz.meerkat.util.basicEval

import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.pieces.Piece
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.position.BasicNode

/**
 * Created by alan on 12/5/14.
 */
object MaterialEvaluation extends Evaluation {

  val pawnScore = 1.0
  val knightScore = 3.0
  val bishopScore = 3.2
  val rookScore = 5.0
  val queenScore = 9.0
  val kingScore = 84.0

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

  def evaluate(node : BasicNode) =
    node.pieces.map(p =>
      if (p.isDefined) score(p.get)
      else 0
    ).sum

}
