package com.alanjz.meerkat.moves

import com.alanjz.meerkat.pieces.Color.White
import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

class PawnMover(val node : MaskNode) extends IntermediateMover {

  /**
   * Gets all pawn attacks.
   *
   * This includes promote-captures.
   *
   * @return the attacks possible by this piece.
   */

  override def getAttacks : BitMask = {

    // get pawns by active color.
    val pawns = node.activePawns

    // moves.
    var moves : BitMask = BitMask.empty

    // left captures.
    moves |= (pawns & ~BitMask.File.A) << 7

    // right captures.
    moves |= (pawns & ~BitMask.File.H) << 9

    // return.
    moves
  }

  /**
   * Gets all pawn advances.
   *
   * This includes home-row pawn advances.
   * This includes promotion moves.
   * \
   * @return all pawn advances.
   */

  def getAdvances : BitMask =

    // get those active pawn advances.
    if(node.active == White) getWhiteAdvances
    else getBlackAdvances

  /**
   * Gets all black pawn advances.
   * @return all black pawn advances.
   */

  private def getBlackAdvances : BitMask = {

    // black pawns.
    val pawns = node.blackPawns

    // pawn moves.
    var moves : BitMask = 0

    // pawn advances
    moves |= pawns >> 8

    // home row advances.
    moves |= (pawns & BitMask.Rank._6) >> 16

    // return.
    moves
  }

  /**
   * Get all white pawn advances.
   *
   * Moves are pseudo-legalized for simplicity.
   *
   * @return all pseudo-legal white pawn advances.
   */

  private def getWhiteAdvances : BitMask = {

    // all pieces.
    val pieces = node.allPieces

    // white pawns.
    val pawns = node.whitePawns

    // pawn moves.
    var moves : BitMask = 0

    // pawn advances
    moves |= pawns << 8

    // get home pawns.
    val homePawns = pawns & BitMask.Rank._2

    // home row advances.
    moves |= (homePawns << 16) & ~(pieces << 8)

    // legalize the moves.
    moves &= ~pieces

    // return.
    moves
  }

  /**
   * Gets all pseudo-legal moves for this piece.
   *
   * This validates captures and advances.
   *
   * But, can leave the king in check.
   *
   * @return all pseudo-legal moves and captures.
   */

  override def getPseudos : BitMask = {

    // get inactive pieces.
    val inactive = node.inactivePieces

    // get active pieces.
    val active = node.activePieces

    // all captures, en passant, and advances.
    getAttacks & (inactive | node.enPassant) & ~active | getAdvances
  }
}
