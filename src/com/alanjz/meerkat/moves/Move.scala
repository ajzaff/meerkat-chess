package com.alanjz.meerkat.moves

import com.alanjz.meerkat._
import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.pieces._

/**
 * The base class for all moves that have an originating square and a target.
 */

sealed trait Move {

  /**
   * The originating square for this move.
   */

  val origin : Square

  /**
   * The target square for this move.
   */

  val target : Square

  /**
   * Converts this move to a string. Many subclasses override this.
   * @return a string representing this move.
   */

  override def toString = s"$origin$target"
}

/**
 * Contains all existing move classes and declarations.
 */

object Move {

  /**
   * A capturing move.
   */

  sealed trait Capture {

    /**
     * The piece which has been captured
     */

    val captured : Piece
  }

  /**
   * A castling move.
   */

  sealed trait Castle {
    val color : Color
  }

  /**
   * A pawn advance.
   * @param origin origin square.
   * @param target target square.
   */

  case class PawnAdvance(val origin : Square, val target : Square) extends Move {

    /**
     * Return only the target square (notation).
     * @return a string representing this move.
     */

    override def toString = target.toString
  }

  /**
   * A pawn capture.
   * @param origin origin square.
   * @param target target square.
   * @param captured captured piece.
   */

  case class PawnCapture(val origin : Square, val target : Square, val captured : Piece) extends Move with Capture {

    /**
     * Return the originating file and target square (notation).
     * @return a string representing this move.
     */

    override def toString = s"${origin.file}x$target"
  }

  /**
   * En passant pawn capture.
   * @param origin origin square.
   * @param target target square.
   */

  case class PawnEPCapture(val origin : Square, val target : Square) extends Move with Capture {

    /**
     * Decide the captured piece color based on move.
     */

    val captured =
      if(target.toInt - origin.toInt > 0) Piece.Pawn(Black)
      else Piece.Pawn(White)

    /**
     * Return the originating file and target square (notation).
     * @return a string representing this move.
     */

    override def toString = s"${origin.file}x$target"
  }

  /**
   * A promotion move.
   * @param origin
   * @param target
   * @param promoted
   */

  case class PawnPromote(val origin : Square, val target : Square, val promoted : Piece with Piece.Promoted) extends Move {

    /**
     * Only target square and promoted piece (notation).
     * @return a string representing this move.
     */

    override def toString = s"$target=${promoted.toChar.toUpper}"
  }

  /**
   * Both promote and capture a piece.
   * @param origin origin square.
   * @param target target square.
   * @param captured captured piece.
   * @param promoted promoted piece.
   */

  case class PawnPromoteCapture(val origin : Square, val target : Square, val captured : Piece,
                                val promoted : Piece) extends Move with Capture {

    /**
     * Only target square, capture, and promoted piece (notation).
     * @return a string representing this move.
     */

    override def toString = s"${origin.file}x$target=${promoted.toChar.toUpper}"
  }

  /**
   * A knight jump.
   * @param origin origin square.
   * @param target target square.
   */

  case class KnightMove(val origin : Square, val target : Square) extends Move {

    /**
     * Gets a file-disambiguating string representation of this move.
     * @return the file-disambiguating string.
     */

    def fileString = s"N${origin.file}$target"

    /**
     * Gets a rank-disambiguating string representation of this move.
     * @return the rank-disambiguating string.
     */

    def rankString = s"N${origin.rank}$target"

    /**
     * Gets a target-disambiguating string representation of this move.
     * @return the target-disambiguating string.
     */

    def originString = s"N$origin$target"

    /**
     * Gets the basic string representing this move.
     * @return a string representing this move.
     */

    override def toString = s"N$target"
  }

  /**
   * A knight capture move.
   * @param origin
   * @param target
   * @param captured
   */

  case class KnightCapture(val origin : Square, val target : Square,
                           val captured : Piece) extends Move with Capture {

    /**
     * Gets a file-disambiguating string representation of this move.
     * @return the file-disambiguating string.
     */

    def fileString = s"N${origin.file}x$target"

    /**
     * Gets a rank-disambiguating string representation of this move.
     * @return the rank-disambiguating string.
     */

    def rankString = s"N${origin.rank}x$target"

    /**
     * Gets a target-disambiguating string representation of this move.
     * @return the target-disambiguating string.
     */

    def originString = s"N${origin}x$target"

    /**
     * Gets the basic string representing this move.
     * @return a string representing this move.
     */

    override def toString = s"Nx$target"
  }

  /**
   * A bishop move.
   * @param origin origin square.
   * @param target target square.
   */

  case class BishopMove(val origin : Square, val target : Square) extends Move {

    /**
     * Gets a file-disambiguating string representation of this move.
     * @return the file-disambiguating string.
     */

    def fileString = s"B${origin.file}$target"

    /**
     * Gets a rank-disambiguating string representation of this move.
     * @return the rank-disambiguating string.
     */

    def rankString = s"B${origin.rank}$target"

    /**
     * Gets a target-disambiguating string representation of this move.
     * @return the target-disambiguating string.
     */

    def originString = s"B$origin$target"

    /**
     * Gets the basic string representing this move.
     * @return a string representing this move.
     */

    override def toString = s"B$target"
  }

  /**
   * A bishop capture.
   * @param origin origin square.
   * @param target target square.
   * @param captured captured piece.
   */

  case class BishopCapture(val origin : Square, val target : Square, val captured : Piece) extends Move with Capture {

    /**
     * Gets a file-disambiguating string representation of this move.
     * @return the file-disambiguating string.
     */

    def fileString = s"B${origin.file}x$target"

    /**
     * Gets a rank-disambiguating string representation of this move.
     * @return the rank-disambiguating string.
     */

    def rankString = s"B${origin.rank}x$target"

    /**
     * Gets a target-disambiguating string representation of this move.
     * @return the target-disambiguating string.
     */

    def originString = s"B${origin}x$target"

    /**
     * Gets the basic string representing this move.
     * @return a string representing this move.
     */

    override def toString = s"Bx$target"
  }

  /**
   * A rook move.
   * @param origin origin square.
   * @param target target square.
   */

  case class RookMove(val origin : Square, val target : Square) extends Move {

    /**
     * Gets a file-disambiguating string representation of this move.
     * @return the file-disambiguating string.
     */

    def fileString = s"R${origin.file}$target"

    /**
     * Gets a rank-disambiguating string representation of this move.
     * @return the rank-disambiguating string.
     */

    def rankString = s"R${origin.rank}$target"

    /**
     * Gets a target-disambiguating string representation of this move.
     * @return the target-disambiguating string.
     */

    def originString = s"R$origin$target"

    /**
     * Gets the basic string representing this move.
     * @return a string representing this move.
     */

    override def toString = s"R$target"
  }

  /**
   * A rook capture.
   * @param origin origin square.
   * @param target target square.
   * @param captured captured piece.
   */

  case class RookCapture(val origin : Square, val target : Square, val captured : Piece) extends Move with Capture {

    /**
     * Gets a file-disambiguating string representation of this move.
     * @return the file-disambiguating string.
     */

    def fileString = s"R${origin.file}x$target"

    /**
     * Gets a rank-disambiguating string representation of this move.
     * @return the rank-disambiguating string.
     */

    def rankString = s"R${origin.rank}x$target"

    /**
     * Gets a target-disambiguating string representation of this move.
     * @return the target-disambiguating string.
     */

    def originString = s"R${origin}x$target"

    /**
     * Gets the basic string representing this move.
     * @return a string representing this move.
     */

    override def toString = s"Rx$target"
  }

  /**
   * A queen move.
   * @param origin origin square.
   * @param target target square.
   */

  case class QueenMove(val origin : Square, val target : Square) extends Move {

    /**
     * Gets a file-disambiguating string representation of this move.
     * @return the file-disambiguating string.
     */

    def fileString = s"Q${origin.file}$target"

    /**
     * Gets a rank-disambiguating string representation of this move.
     * @return the rank-disambiguating string.
     */

    def rankString = s"Q${origin.rank}$target"

    /**
     * Gets a target-disambiguating string representation of this move.
     * @return the target-disambiguating string.
     */

    def originString = s"Q$origin$target"

    /**
     * Gets the basic string representing this move.
     * @return a string representing this move.
     */

    override def toString = s"Q$target"
  }

  /**
   * A queen capture.
   * @param origin origin square.
   * @param target target square.
   * @param captured captured piece.
   */

  case class QueenCapture(val origin : Square, val target : Square, val captured : Piece) extends Move with Capture {

    /**
     * Gets a file-disambiguating string representation of this move.
     * @return the file-disambiguating string.
     */

    def fileString = s"Q${origin.file}x$target"

    /**
     * Gets a rank-disambiguating string representation of this move.
     * @return the rank-disambiguating string.
     */

    def rankString = s"Q${origin.rank}x$target"

    /**
     * Gets a target-disambiguating string representation of this move.
     * @return the target-disambiguating string.
     */

    def originString = s"Q${origin}x$target"

    /**
     * Gets the basic string representing this move.
     * @return a string representing this move.
     */

    override def toString = s"Qx$target"
  }

  /**
   * A king move.
   * @param origin origin square.
   * @param target target square.
   */

  case class KingMove(val origin : Square, val target : Square) extends Move {

    /**
     * Gets a string representation for this move.
     * @return a string representing this move.
     */

    override def toString = s"K$target"
  }

  /**
   * A king capture.
   * @param origin origin square.
   * @param target target square.
   * @param captured captured piece.
   */

  case class KingCapture(val origin : Square, val target : Square, val captured : Piece) extends Move with Capture {

    /**
     * Gets a string representation for this move.
     * @return a string representing this move.
     */

    override def toString = s"Kx$target"
  }

  /**
   * A short castle move.
   * @param color active team.
   */
  
  case class KingCastleShort(val color : Color) extends Move with Move.Castle {

    /**
     * Set the origin based on color.
     */

    val origin = color match {
      case White => Square.E1
      case Black => Square.E8
    }

    /**
     * Set the target based on color.
     */

    val target = color match {
      case White => Square.G1
      case Black => Square.G8
    }

    /**
     * Calculates a hash code for this castle move
     * @return a hash code integer.
     */

    override def hashCode : Int = color.toInt * 0x9a349

    /**
     * Represents this short castle as a string.
     * @return a string representing this move.
     */

    override def toString = "O-O"
  }

  /**
   * A long castle move.
   * @param color active team.
   */

  case class KingCastleLong(val color : Color) extends Move with Move.Castle {

    /**
     * Set the origin based on color.
     */

    val origin = color match {
      case White => Square.E1
      case Black => Square.E8
    }

    /**
     * Set the target based on color.
     */

    val target = color match {
      case White => Square.C1
      case Black => Square.C8
    }

    /**
     * Calculates a hash code for this castle move
     * @return a hash code integer.
     */

    override def hashCode : Int = color.toInt * 0xa23ad

    /**
     * Represents this short castle as a string.
     * @return a string representing this move.
     */

    override def toString = "O-O-O"
  }
}

