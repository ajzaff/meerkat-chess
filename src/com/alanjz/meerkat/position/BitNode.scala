package com.alanjz.meerkat.position

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.moves.{Move, CastleRule}
import com.alanjz.meerkat.pieces.Color
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.util.numerics.BitMask.BitMask
import com.alanjz.meerkat.util.parsing.FENParser.FEN

class BitNode(val allPieces : BitMask,
              val whitePieces : BitMask,
              val whitePawns : BitMask,
              val whiteKnights : BitMask,
              val whiteBishops : BitMask,
              val whiteRooks : BitMask,
              val whiteQueens : BitMask,
              val whiteKing : BitMask,
              val blackPieces : BitMask,
              val blackPawns : BitMask,
              val blackKnights : BitMask,
              val blackBishops : BitMask,
              val blackRooks : BitMask,
              val blackQueens : BitMask,
              val blackKing : BitMask,
              val active : Color,
              val castleRules : CastleRule,
              val enPassant : Option[Square],
              val halfMove : Int,
              val fullMove : Int) extends Node {

  val activePieces = active match {
    case White => whitePieces
    case Black => blackPieces
  }

  val activePawns = active match {
    case White => whitePawns
    case Black => blackPawns
  }

  val activeKnights = active match {
    case White => whiteKnights
    case Black => blackKnights
  }

  val activeBishops = active match {
    case White => whiteBishops
    case Black => blackBishops
  }

  val activeRooks = active match {
    case White => whiteRooks
    case Black => blackRooks
  }

  val activeQueens = active match {
    case White => whiteQueens
    case Black => blackQueens
  }

  val activeKing = active match {
    case White => whiteKing
    case Black => blackKing
  }

  val inactivePieces = active match {
    case Black => whitePieces
    case White => blackPieces
  }

  val inactivePawns = active match {
    case Black => whitePawns
    case White => blackPawns
  }

  val inactiveKnights = active match {
    case Black => whiteKnights
    case White => blackKnights
  }

  val inactiveBishops = active match {
    case Black => whiteBishops
    case White => blackBishops
  }

  val inactiveRooks = active match {
    case Black => whiteRooks
    case White => blackRooks
  }

  val inactiveQueens = active match {
    case Black => whiteQueens
    case White => blackQueens
  }

  val inactiveKing = active match {
    case Black => whiteKing
    case White => blackKing
  }

  /**
   * Tries to apply the given move.
   * @param move a move.
   * @return a node.
   */

  override def move(move: Move): BasicNode = ???

  /**
   * Returns an instance of this node with `color` to move.
   * @param color a color.
   * @return a new node with `color` to move.
   */

  override def as(color: Color): BasicNode = ???

  override val FEN: FEN = ???
}

object BitNode {

}