package com.alanjz.meerkat.moves

import com.alanjz.meerkat.moves.Move._
import com.alanjz.meerkat.pieces.Color._

/**
 * Represents the right to castle in chess.
 * @param moves the set of allowed castling moves.
 */

sealed case class CastleRule(moves : Set[Move.Castle]) {

  /**
   * Tests whether this rule allows the given castle move.
   * @param move a castling move.
   * @return `true` if allowed; `false` otherwise.
   */

  def contains(move : Move.Castle) : Boolean = moves.contains(move)

  /**
   * Returns the union of this rule with another.
   * @param rhs a castling rule.
   * @return a new castling rule representing the union of this and `rhs`.
   */

  def +(rhs : CastleRule) = new CastleRule(moves | rhs.moves)

  /**
   * Returns the union of this rule and a castling move.
   * @param rhs a castling move.
   * @return a new castling rule representing the union of this and `rhs`.
   */

  def +(rhs : Move.Castle) = new CastleRule(moves + rhs)

  /**
   * Returns this rule without the given move.
   * @param rhs a move.
   * @return a new rule.
   */

  def -(rhs : Move.Castle) = new CastleRule(moves - rhs)

  /**
   * Builds a string of this rule.
   * @return a string.
   */

  override def toString : String = {
    val builder = StringBuilder.newBuilder
    if(moves.isEmpty) return "-"
    else {
      if(contains(KingCastleShort(White))) builder += 'K'
      if(contains(KingCastleLong(White))) builder += 'Q'
      if(contains(KingCastleShort(Black))) builder += 'k'
      if(contains(KingCastleLong(Black))) builder += 'q'
    }
    builder.mkString
  }
}

/**
 * Castle rule utilities and usable values.
 */

object CastleRule {

  /**
   * Creates a castle rule from the given sequence of castling moves.
   * @param moves a sequence of castling moves.
   * @return a castle rule representing the moves.
   */

  def from(moves : Move.Castle*) = new CastleRule(moves.toSet)

  /**
   * All possible castle moves.
   */

  val all = from(KingCastleShort(White), KingCastleShort(Black), KingCastleLong(White), KingCastleLong(Black))

  /**
   * No castling allowed.
   */

  val empty = from()
}