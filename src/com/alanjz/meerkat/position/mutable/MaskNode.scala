package com.alanjz.meerkat.position.mutable

import com.alanjz.meerkat.util.position.mutable.{FENBuilder, NodeStringBuilder, FENMaskNodeBuilder}
import com.alanjz.meerkat.{Square, Rank}
import com.alanjz.meerkat.moves.{Attacker, Move}
import com.alanjz.meerkat.moves.Move._
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.pieces.{Piece, Color}
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.util.numerics.CastleMask.CastleMask
import com.alanjz.meerkat.util.numerics.{CastleMask, BitMask}
import com.alanjz.meerkat.util.numerics.BitMask.BitMask
import FENBuilder.FEN
import com.alanjz.meerkat.util.position.mutable.{FENBuilder, NodeStringBuilder, FENMaskNodeBuilder}

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

  private var enPassantList : List[BitMask] = Nil
  private var halfMoveList : List[Int] = Nil
  private var moveList : List[Move] = Nil
  private var castleMaskList : List[CastleMask] = Nil

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

  override def FEN: FEN = {
    new FENBuilder(this).mkFEN
  }

  def isTerminal : Boolean = inCheck(!active)

  def inCheck(who : Color) : Boolean = {
    var check = false
    if(who != active) {
      active = ! active
      check = new Attacker(this).getAttackers(who match {
        case White => whiteKing
        case Black => blackKing
      }) != BitMask.empty
      active = ! active
    } else
      check = new Attacker(this).getAttackers(who match {
        case White => whiteKing
        case Black => blackKing
      }) != BitMask.empty
    check
  }

  private def flipPiece(piece : Piece, origin : Square, target : Square): Unit = {
    allPieces ^= (1l << origin.toInt) | (1l << target.toInt)
    piece match {
      case Pawn(White) =>
        whitePawns ^= (1l << origin.toInt) | (1l << target.toInt)
        whitePieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Knight(White) =>
        whiteKnights ^= (1l << origin.toInt) | (1l << target.toInt)
        whitePieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Bishop(White) =>
        whiteBishops ^= (1l << origin.toInt) | (1l << target.toInt)
        whitePieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Rook(White) =>
        whiteRooks ^= (1l << origin.toInt) | (1l << target.toInt)
        whitePieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Queen(White) =>
        whiteQueens ^= (1l << origin.toInt) | (1l << target.toInt)
        whitePieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case King(White) =>
        whiteKing ^= (1l << origin.toInt) | (1l << target.toInt)
        whitePieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Pawn(Black) =>
        blackPawns ^= (1l << origin.toInt) | (1l << target.toInt)
        blackPieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Knight(Black) =>
        blackKnights ^= (1l << origin.toInt) | (1l << target.toInt)
        blackPieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Bishop(Black) =>
        blackBishops ^= (1l << origin.toInt) | (1l << target.toInt)
        blackPieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Rook(Black) =>
        blackRooks ^= (1l << origin.toInt) | (1l << target.toInt)
        blackPieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case Queen(Black) =>
        blackQueens ^= (1l << origin.toInt) | (1l << target.toInt)
        blackPieces ^= (1l << origin.toInt) | (1l << target.toInt)
      case King(Black) =>
        blackKing ^= (1l << origin.toInt) | (1l << target.toInt)
        blackPieces ^= (1l << origin.toInt) | (1l << target.toInt)
    }
  }
  private def flip(piece : Piece, at : Square) : Unit = flipPiece(piece, at, at)

  /**
   * Tries to apply the given move.
   * @param move a move.
   * @return a node.
   */

  override def make(move: Move) : Unit = {

    // Prepend the move state information.
    enPassantList +:= enPassant
    halfMoveList +:= halfMove
    moveList +:= move
    castleMaskList +:= castleMask

    // Increment the half move clock by default.
    // It may be zeroed out at some point later.
    // Also reset the enPassant square, though
    // like halfMove may be reset at a later time.
    halfMove += 1
    enPassant = BitMask.empty

    // If black is to move, we can increment the
    // Full move clock, since his move ends turn.
    if(active == Black) fullMove += 1

    move match {
      case PawnPromote(o,t,p) =>
        // Reset the half move counter.
        halfMove = 0

        flip(Pawn(active), o)
        flip(p, t)
      case PawnPromoteCapture(o,t,c,p) =>
        // Reset the half move counter.
        halfMove = 0

        flip(Pawn(active), o)
        flip(c, t)
        flip(p, t)
      case PawnEPCapture(o, t) =>
        // Reset the half-move clock.
        halfMove = 0

        flipPiece(Pawn(active), o, t)
        // Left capture.
        if (o.file.toInt - t.file.toInt == 1) {
          flip(Pawn(!active), Square(o.toInt - 1))
        } /* Right capture. */
        else {
          flip(Pawn(!active), Square(o.toInt + 1))
        }
      case KingCastleShort(White) =>
        flipPiece(King(White), Square.E1, Square.G1)
        flipPiece(Rook(White), Square.H1, Square.F1)
        castleMask &= ~(CastleMask.shortWhite | CastleMask.longWhite)
      case KingCastleLong(White) =>
        flipPiece(King(White), Square.E1, Square.C1)
        flipPiece(Rook(White), Square.A1, Square.D1)
        castleMask &= ~(CastleMask.shortWhite | CastleMask.longWhite)
      case KingCastleShort(Black) =>
        flipPiece(King(Black), Square.E8, Square.G8)
        flipPiece(Rook(Black), Square.H8, Square.F8)
        castleMask &= ~(CastleMask.shortBlack | CastleMask.longBlack)
      case KingCastleLong(Black) =>
        flipPiece(King(Black), Square.E8, Square.C8)
        flipPiece(Rook(Black), Square.A8, Square.D8)
        castleMask &= ~(CastleMask.shortBlack | CastleMask.longBlack)
      case m: Capture =>

        val originPiece = at(m.origin.toInt).get
        originPiece match {
          case Rook(_) =>
            m.origin match {
              case Square.A1 => castleMask &= ~CastleMask.longWhite
              case Square.H1 => castleMask &= ~CastleMask.shortWhite
              case Square.A8 => castleMask &= ~CastleMask.longBlack
              case Square.H8 => castleMask &= ~CastleMask.shortBlack
              case _ =>
            }
          case King(_) =>
            active match {
              case White => castleMask &= ~(CastleMask.shortWhite | CastleMask.longWhite)
              case Black => castleMask &= ~(CastleMask.shortBlack | CastleMask.longBlack)
            }
          case _ =>
        }

        flip(m.captured, m.target)
        flipPiece(originPiece, m.origin, m.target)

        // Reset the half move counter.
        halfMove = 0
      case m: Move =>
        if(at(m.origin.toInt).isEmpty) {
          println(this)
          throw new IllegalStateException(s"in make move `${m.origin}${m.target}': no piece at origin.")
        }
        flipPiece(at(m.origin.toInt).get, m.origin, m.target)
        m match {
          case PawnAdvance(o,t) =>
            // Reset the half-move counter.
            halfMove = 0

            // Update en passant squares.
            if(active == White && o.rank == Rank._2 && t.rank == Rank._4) {
              enPassant = 1l << (t.toInt-8)
            }
            else if(active == Black && t.rank == Rank._5 && o.rank == Rank._7) {
              enPassant = 1l << (t.toInt+8)
            }
          case RookMove(o,t) =>
            o match {
              case Square.A1 => castleMask &= ~CastleMask.longWhite
              case Square.H1 => castleMask &= ~CastleMask.shortWhite
              case Square.A8 => castleMask &= ~CastleMask.longBlack
              case Square.H8 => castleMask &= ~CastleMask.shortBlack
              case _ =>
            }
          case KingMove(o,t) =>
            active match {
              case White => castleMask &= ~(CastleMask.shortWhite | CastleMask.longWhite)
              case Black => castleMask &= ~(CastleMask.shortBlack | CastleMask.longBlack)
            }
          case _ =>
        }
    }

    // Update the team to move.
    active = !active
  }

  /**
   * Tries to unapply the given move.
   * @return a node.
   */

  override def unmake() : Unit = {
    if(moveList.isEmpty) return

    // Restore the states of the detail variables.
    // And get the last move from the moves list.
    enPassant = enPassantList.head
    halfMove = halfMoveList.head
    castleMask = castleMaskList.head
    if(active == White) fullMove -= 1
    val lastMove = moveList.head

    // Pop off the details from the lists.
    enPassantList = enPassantList.tail
    halfMoveList = halfMoveList.tail
    castleMaskList = castleMaskList.tail
    moveList = moveList.tail

    // Restore the active color.
    active = !active

    lastMove match {
      case PawnPromote(o,t,p) =>
        flip(Pawn(active), o)
        flip(p, t)
      case PawnPromoteCapture(o,t,c,p) =>
        flip(Pawn(active), o)
        flip(c, t)
        flip(p, t)
      case PawnEPCapture(o, t) =>
        flipPiece(Pawn(active), t.toInt, o.toInt)

        // Left capture.
        if (o.file.toInt - t.file.toInt == 1) {
          flip(Pawn(!active), Square(o.toInt - 1))
        } /* Right capture. */
        else {
          flip(Pawn(!active), Square(o.toInt + 1))
        }

      case KingCastleShort(White) =>
        flipPiece(King(White), Square.E1, Square.G1)
        flipPiece(Rook(White), Square.H1, Square.F1)
      case KingCastleLong(White) =>
        flipPiece(King(White), Square.E1, Square.C1)
        flipPiece(Rook(White), Square.A1, Square.D1)
      case KingCastleShort(Black) =>
        flipPiece(King(Black), Square.E8, Square.G8)
        flipPiece(Rook(Black), Square.H8, Square.F8)
      case KingCastleLong(Black) =>
        flipPiece(King(Black), Square.E8, Square.C8)
        flipPiece(Rook(Black), Square.A8, Square.D8)
      case m: Capture =>
        val targetPiece = at(m.target.toInt).get
        flipPiece(targetPiece, m.origin, m.target)
        flip(m.captured, m.target)
      case m: Move =>
        flipPiece(at(m.target.toInt).get, m.origin, m.target)
    }
  }

  def details : String = {
    val builder = StringBuilder.newBuilder
    builder ++= s"${active.toString.head} ${CastleMask.mkString(castleMask)} "
    if(enPassant != BitMask.empty)
      builder ++= s"${Square(BitMask.bitScanForward(enPassant))} "
    else builder ++= "- "
    builder ++= s"$halfMove $fullMove"
    builder.mkString
  }

  /**
   * Represents this node as a string
   * for debugging and printing.
   *
   * @return a string representation of this node
   */
  override def toString : String = {
    val builder = StringBuilder.newBuilder
    builder ++= NodeStringBuilder.mkString(this)
    builder ++= FEN
    if(inCheck(active)) builder ++= " (+)"
    if(isTerminal) builder ++= " (terminal)"
    builder.mkString
  }
}

object MaskNode {
  def empty = new MaskNode()
  def initialPosition = FENMaskNodeBuilder.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
}