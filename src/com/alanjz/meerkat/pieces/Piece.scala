package com.alanjz.meerkat.pieces

import com.alanjz.meerkat.pieces.Color._

/**
 * The base class of all colored pieces.
 */

sealed trait Piece {

  /**
   * The name of this piece.
   */

  val name : String

  /**
   * The color of this piece.
   */

  val color : Color
}

object Piece {

  /**
   * All pieces which can be promoted to.
   */

  sealed trait Promoted

  /**
   * A pawn.
   * @param color a color.
   */

  case class Pawn(val color : Color) extends Piece {
    val name = "pawn"
  }

  /**
   * A knight.
   * @param color a color.
   */

  case class Knight(val color : Color) extends Piece with Promoted {
    val name = "knight"
  }

  /**
   * A bishop.
   * @param color a color.
   */

  case class Bishop(val color : Color) extends Piece with Promoted {
    val name = "bishop"
  }

  /**
   * A rook.
   * @param color a color.
   */

  case class Rook(val color : Color) extends Piece with Promoted {
    val name = "rook"
  }

  /**
   * A queen.
   * @param color a color.
   */

  case class Queen(val color : Color) extends Piece with Promoted {
    val name = "queen"
  }

  /**
   * A king.
   * @param color a color.
   */

  case class King(val color : Color) extends Piece {
    val name = "king"
  }

  /**
   * Converts a piece to its character class.
   * @param lhs a piece.
   * @return a character representing the colored piece.
   */

  implicit def Piece2Char(lhs : Piece) : Char = lhs match {
    case Pawn(White) => 'P'
    case Knight(White) => 'N'
    case Bishop(White) => 'B'
    case Rook(White) => 'R'
    case Queen(White) => 'Q'
    case King(White) => 'K'
    case Pawn(Black) => 'p'
    case Knight(Black) => 'n'
    case Bishop(Black) => 'b'
    case Rook(Black) => 'r'
    case Queen(Black) => 'q'
    case King(Black) => 'k'
  }
}