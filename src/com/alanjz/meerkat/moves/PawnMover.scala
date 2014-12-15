package com.alanjz.meerkat.moves

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.moves.Move._
import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.pieces.Piece.{Bishop, Rook, Knight, Queen}
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

  override def getAttacks(pawns : BitMask) : BitMask = {

    // moves.
    var moves : BitMask = BitMask.empty

    // left captures.
    moves |= getLeftAttacks(pawns)

    // right captures.
    moves |= getRightAttacks(pawns)

    // return.
    moves
  }

  def getLeftAttacks(pawns : BitMask) : BitMask = {

    // moves.
    var moves : BitMask = BitMask.empty

    // left captures.
    if(node.active == White) moves |= (pawns & ~BitMask.File.A) << 7
    else moves |= (pawns & ~BitMask.File.A) >> 9

    // return.
    moves
  }

  def getRightAttacks(pawns : BitMask) : BitMask = {

    // moves.
    var moves : BitMask = BitMask.empty

    // left captures.
    if(node.active == White) moves |= (pawns & ~BitMask.File.H) << 9
    else moves |= (pawns & ~BitMask.File.H) >> 7

    // return.
    moves
  }

  /**
   * Gets all pawn advances.
   *
   * Moves are pseudo-legalized.
   *
   * This includes home-row pawn advances.
   * This includes promotion moves.
   *
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

    // all pieces.
    val pieces = node.allPieces

    // black pawns.
    val pawns = node.blackPawns

    // pawn moves.
    var moves : BitMask = 0

    // pawn advances
    moves |= pawns >> 8

    // get home pawns.
    val homePawns = pawns & BitMask.Rank._7

    // home row advances.
    moves |= (homePawns >> 16) & ~(pieces >> 8)

    // legalize the moves.
    moves &= ~pieces

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
    getAttacks(node.activePawns) & ((inactive & ~active) | node.enPassant) | getAdvances
  }

  /**
   * Serializes the pseudo-legal moves.
   * @return a list of pseudo-legal moves of the appropriate type.
   */
  override def mkList: List[Move] = {

    var builder = List.newBuilder[Move]
    val activePawns = node.activePawns
    var moves = getPseudos

    while(moves != BitMask.empty) {
      val lsb = BitMask.bitScanForward(moves)


      // ===========================White side.========================================
      if(node.active == White) {

        // En-passant.
        if((1l << lsb) == node.enPassant) {

          // Left capture.
          if((lsb+1) % 8 != 0 && (activePawns & (1l << (lsb-7))) > BitMask.empty) {
            builder += PawnEPCapture(lsb-7, lsb)
          }

          // Right capture.
          if(lsb % 8 != 0 && (activePawns & (1l << (lsb-9))) > BitMask.empty) {
            builder += PawnEPCapture(lsb-9, lsb)
          }
        }

        // This must be a pawn advance.
        else if(node.empty(lsb)) {

          // Double advance.
          if((activePawns & (1l << (lsb-16))) > BitMask.empty) {
            builder += PawnAdvance(lsb-16, lsb)
          }
          else {
            if(lsb >= Square.A8.toInt) {
              builder += PawnPromote(lsb-8, lsb, Queen(White))
              builder += PawnPromote(lsb-8, lsb, Knight(White))
              builder += PawnPromote(lsb-8, lsb, Rook(White))
              builder += PawnPromote(lsb-8, lsb, Bishop(White))
            }
            else {
              builder += PawnAdvance(lsb-8, lsb)
            }

          }
        }

        // This must be a pawn capture.
        else {

          // Left capture.
          if((lsb+1) % 8 != 0 && (activePawns & (1l << (lsb-7))) > BitMask.empty) {
            if(lsb >= Square.A8.toInt) {
              builder += PawnPromoteCapture(lsb-7, lsb, node.at(lsb).get, Queen(White))
              builder += PawnPromoteCapture(lsb-7, lsb, node.at(lsb).get, Knight(White))
              builder += PawnPromoteCapture(lsb-7, lsb, node.at(lsb).get, Rook(White))
              builder += PawnPromoteCapture(lsb-7, lsb, node.at(lsb).get, Bishop(White))
            }
            else {
              builder += PawnCapture(lsb-7, lsb, node.at(lsb).get)
            }

          }

          // Right capture.
          if(lsb % 8 != 0 && (activePawns & (1l << (lsb-9))) > BitMask.empty) {
            if(lsb >= Square.A8.toInt) {
              builder += PawnPromoteCapture(lsb-9, lsb, node.at(lsb).get, Queen(White))
              builder += PawnPromoteCapture(lsb-9, lsb, node.at(lsb).get, Knight(White))
              builder += PawnPromoteCapture(lsb-9, lsb, node.at(lsb).get, Rook(White))
              builder += PawnPromoteCapture(lsb-9, lsb, node.at(lsb).get, Bishop(White))
            }
            else {
              builder += PawnCapture(lsb-9, lsb, node.at(lsb).get)
            }
          }
        }
      }

      // =========================Black side.================================
      else {

        // En-passant.
        if((1l << lsb) == node.enPassant) {

          // Left capture.
          if((lsb+1) % 8 != 0 && (activePawns & (1l << (lsb+9))) > BitMask.empty) {
            builder += PawnEPCapture(lsb+9, lsb)
          }

          // Right capture.
          if(lsb % 8 != 0 && (activePawns & (1l << (lsb+7))) > BitMask.empty) {
            builder += PawnEPCapture(lsb+7, lsb)
          }
        }

        // This must be a pawn advance.
        else if(node.empty(lsb)) {

          // Double advance.
          if((activePawns & (1l << (lsb+16))) > BitMask.empty) {
            builder += PawnAdvance(lsb+16, lsb)
          }
          else {
            if(lsb <= Square.H1.toInt) {
              builder += PawnPromote(lsb+8, lsb, Queen(Black))
              builder += PawnPromote(lsb+8, lsb, Knight(Black))
              builder += PawnPromote(lsb+8, lsb, Rook(Black))
              builder += PawnPromote(lsb+8, lsb, Bishop(Black))
            }
            else {
              builder += PawnAdvance(lsb+8, lsb)
            }

          }
        }

        // This must be a pawn capture.
        else {

          // Left capture.
          if((lsb+1) % 8 != 0 && (activePawns & (1l << (lsb+9))) > BitMask.empty) {
            if(lsb <= Square.H1.toInt) {
              builder += PawnPromoteCapture(lsb+9, lsb, node.at(lsb).get, Queen(Black))
              builder += PawnPromoteCapture(lsb+9, lsb, node.at(lsb).get, Knight(Black))
              builder += PawnPromoteCapture(lsb+9, lsb, node.at(lsb).get, Rook(Black))
              builder += PawnPromoteCapture(lsb+9, lsb, node.at(lsb).get, Bishop(Black))
            }
            else {
              builder += PawnCapture(lsb+9, lsb, node.at(lsb).get)
            }

          }

          // Right capture.
          if(lsb % 8 != 0 && (activePawns & (1l << (lsb+7))) > BitMask.empty) {
            if(lsb <= Square.H1.toInt) {
              builder += PawnPromoteCapture(lsb+7, lsb, node.at(lsb).get, Queen(Black))
              builder += PawnPromoteCapture(lsb+7, lsb, node.at(lsb).get, Knight(Black))
              builder += PawnPromoteCapture(lsb+7, lsb, node.at(lsb).get, Rook(Black))
              builder += PawnPromoteCapture(lsb+7, lsb, node.at(lsb).get, Bishop(Black))
            }
            else {
              builder += PawnCapture(lsb+7, lsb, node.at(lsb).get)
            }
          }
        }
      }
      moves &= (moves-1)
    }


    builder.result()
  }
}
