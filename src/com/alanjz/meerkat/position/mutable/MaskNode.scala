package com.alanjz.meerkat.position.mutable

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.moves.{CastleRule, Move}
import com.alanjz.meerkat.pieces.Color
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.position.immutable.BasicNode
import com.alanjz.meerkat.util.numerics.CastleMask
import com.alanjz.meerkat.util.numerics.CastleMask.CastleMask
import com.alanjz.meerkat.util.numerics.{CastleMask, BitMask}
import com.alanjz.meerkat.util.numerics.BitMask.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask
import com.alanjz.meerkat.util.position.immutable.FENParser
import FENParser.FEN
import com.alanjz.meerkat.util.position.immutable.FENParser

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
  var active : Color = White
  var castleRules : CastleMask = CastleMask.all
  var enPassant : Option[Square] = None
  var halfMove : Int = 0
  var fullMove : Int = 1

  var activePawns : BitMask = BitMask.empty
  var activeKnights : BitMask = BitMask.empty
  var activeBishops : BitMask = BitMask.empty
  var activeRooks : BitMask = BitMask.empty
  var activeQueens : BitMask = BitMask.empty
  var activeKing : BitMask = BitMask.empty
  var inactivePieces : BitMask = BitMask.empty
  var inactivePawns : BitMask = BitMask.empty
  var inactiveKnights : BitMask = BitMask.empty
  var inactiveBishops : BitMask = BitMask.empty
  var inactiveRooks : BitMask = BitMask.empty
  var inactiveQueens : BitMask = BitMask.empty
  var inactiveKing : BitMask = BitMask.empty

  override def FEN: FEN = ???

  /**
   * Tries to apply the given move.
   * @param move a move.
   * @return a node.
   */

  override def make(move: Move) : MaskNode = ???

  /**
   * Tries to unapply the given move.
   * @param move a move.
   * @return a node.
   */

  override def unmake(move : Move) : MaskNode = ???
}

object MaskNode {
  def empty = new MaskNode()
}