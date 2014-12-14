package com.alanjz.meerkat.position.mutable

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.moves.{CastleRule, Move}
import com.alanjz.meerkat.pieces._
import com.alanjz.meerkat.util.parsing.FENParser.FEN

/**
 * The minimal base class of all chess position nodes.
 */

trait Node {

  /**
   * The current active color.
   */

  val active : Color

  /**
   * The full-move number.
   */

  val fullMove : Int

  /**
   * The half-move counter.
   */

  val halfMove : Int

  /**
   * The castle rights for both sides.
   */
  
  val castleRules : CastleRule

  /**
   * The en passant square.
   */

  val enPassant : Option[Square]

  /**
   * A `FEN` string for this position.
   */

  val FEN : FEN

  /**
   * Returns an instance of this node with `color` to move.
   * @param color a color.
   * @return a new node with `color` to move.
   */

  def as(color : Color) : BasicNode

  /**
   * Tries to apply the given move.
   * @param move a move.
   * @return a node.
   */

  def move(move : Move) : BasicNode
}
