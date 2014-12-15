package com.alanjz.meerkat.position.mutable

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.pieces.{Piece, Color}
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.util.numerics.CastleMask.CastleMask
import com.alanjz.meerkat.util.numerics.{CastleMask, BitMask}
import com.alanjz.meerkat.util.numerics.BitMask.BitMask
import com.alanjz.meerkat.util.position.FENBuilder.FEN
import com.alanjz.meerkat.util.position.mutable.FENMaskNodeBuilder

class MaskNode extends Node {

  var allPieces : BitMask = BitMask.empty
  var whitePieces : BitMask = BitMask.empty
  var whitePawns : BitMask = BitMask.empty
  var whiteKnights : BitMask = BitMask.empty
  var whiteBishops : BitMask = BitMask.empty
  var whiteRooks : BitMask = BitMask.empty
  var whiteQueens : BitMask = BitMask.empty
  var whiteKing : BitMask = BitMask.empty
  var blackPieces : BitMask = BitMask.empty
  var blackPawns : BitMask = BitMask.empty
  var blackKnights : BitMask = BitMask.empty
  var blackBishops : BitMask = BitMask.empty
  var blackRooks : BitMask = BitMask.empty
  var blackQueens : BitMask = BitMask.empty
  var blackKing : BitMask = BitMask.empty
  override var active : Color = White
  override var castleMask : CastleMask = CastleMask.all
  override var enPassant : BitMask = BitMask.empty
  override var halfMove : Int = 0
  override var fullMove : Int = 1

  def activePieces : BitMask = active match { case White => whitePieces; case Black => blackPieces }
  def activePawns : BitMask = active match { case White => whitePawns; case Black => blackPawns }
  def activeKnights : BitMask = active match { case White => whiteKnights; case Black => blackKnights }
  def activeBishops : BitMask = active match { case White => whiteBishops; case Black => blackBishops }
  def activeRooks : BitMask = active match { case White => whiteRooks; case Black => blackRooks }
  def activeQueens : BitMask = active match { case White => whiteQueens; case Black => blackQueens }
  def activeKing : BitMask = active match { case White => whiteKing; case Black => blackKing }
  def inactivePieces : BitMask = active match { case Black => whitePieces; case White => blackPieces }
  def inactivePawns : BitMask = active match { case Black => whitePawns; case White => blackPawns }
  def inactiveKnights : BitMask = active match { case Black => whiteKnights; case White => blackKnights }
  def inactiveBishops : BitMask = active match { case Black => whiteBishops; case White => blackBishops }
  def inactiveRooks : BitMask = active match { case Black => whiteRooks; case White => blackRooks }
  def inactiveQueens : BitMask = active match { case Black => whiteQueens; case White => blackQueens }
  def inactiveKing : BitMask = active match { case Black => whiteKing; case White => blackKing }


  def empty(pos : BitMask) : Boolean = (allPieces & pos) == BitMask.empty

  def empty(i : Int) : Boolean = empty(1l << i)

  def at(i : Int) : Option[Piece] = at(1l << i)

  def at(pos : BitMask) : Option[Piece] = {
    if((pos & whitePieces) != BitMask.empty) {
      if((pos & whitePawns) != BitMask.empty) {
        Some(Pawn(White))
      }
      else if((pos & whiteKnights) != BitMask.empty) {
        Some(Knight(White))
      }
      else if((pos & whiteBishops) != BitMask.empty) {
        Some(Bishop(White))
      }
      else if((pos & whiteRooks) != BitMask.empty) {
        Some(Rook(White))
      }
      else if((pos & whiteQueens) != BitMask.empty) {
        Some(Queen(White))
      }
      else if((pos & whiteKing) != BitMask.empty) {
        Some(King(White))
      }
      else throw new IllegalStateException("Illegal state: `whitePieces' mask.")
    }
    else if((pos & blackPieces) != BitMask.empty) {
      if((pos & blackPawns) != BitMask.empty) {
        Some(Pawn(Black))
      }
      else if((pos & blackKnights) != BitMask.empty) {
        Some(Knight(Black))
      }
      else if((pos & blackBishops) != BitMask.empty) {
        Some(Bishop(Black))
      }
      else if((pos & blackRooks) != BitMask.empty) {
        Some(Rook(Black))
      }
      else if((pos & blackQueens) != BitMask.empty) {
        Some(Queen(Black))
      }
      else if((pos & blackKing) != BitMask.empty) {
        Some(King(Black))
      }
      else throw new IllegalStateException("Illegal state: `blackPieces' mask.")
    }
    else None
  }

  override def FEN: FEN = ???

  /**
   * Tries to apply the given move.
   * @param move a move.
   * @return a node.
   */

  override def make(move: Move) : Unit = ???

  /**
   * Tries to unapply the given move.
   * @param move a move.
   * @return a node.
   */

  override def unmake(move : Move) : Unit = ???
}

object MaskNode {
  def empty = new MaskNode()
  def initialPosition = FENMaskNodeBuilder.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
}