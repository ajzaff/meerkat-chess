package com.alanjz.meerkat.position.mutable

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces._
import com.alanjz.meerkat.util.numerics.BitMask.BitMask
import com.alanjz.meerkat.util.numerics.CastleMask.CastleMask
import com.alanjz.meerkat.util.position.mutable.FENBuilder
import FENBuilder.FEN

/**
 * The minimal base class of all chess position nodes.
 */

trait Node {

  /**
   * The current active color.
   */

  var active : Color

  /**
   * The full-move number.
   */

  var fullMove : Int

  /**
   * The half-move counter.
   */

  var halfMove : Int

  /**
   * The castle rights for both sides.
   */
  
  var castleMask : CastleMask

  /**
   * The en passant square.
   */

  var enPassant : BitMask

  /**
   * A `FEN` string for this position.
   */

  def FEN : FEN

  /**
   * Tries to apply the given move.
   * @param move a move.
   * @return a node.
   */

  def make(move : Move) : Unit

  /**
   * Tries to unapply the given move.
   * @return a node.
   */

  def unmake() : Unit
}
