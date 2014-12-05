package com.alanjz.meerkat.position

import com.alanjz.meerkat.moves.Move._
import com.alanjz.meerkat.moves.{CastleRule, Move}
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.pieces.{Color, Piece}
import com.alanjz.meerkat.util.NodeBuilder
import com.alanjz.meerkat.util.basicEval.BasicEvaluation
import com.alanjz.meerkat.util.basicMove.MoveGenerator
import com.alanjz.meerkat.util.parsing.FENParser.FEN
import com.alanjz.meerkat.util.strings.NodeStringBuilder
import com.alanjz.meerkat.{File, Rank, Square}

/**
 * A basic chess position node.
 * @param pieces board configuration.
 * @param fullMove full-move number.
 * @param halfMove half-move counter.
 * @param active active color (to move).
 * @param castleRules castle rights.
 * @param enPassant en passant square or `None`.
 */

class BasicNode(val pieces : List[Option[Piece]] = List.fill(64) (None),
                val fullMove : Int = 1,
                val halfMove : Int = 0,
                val active : Color = White,
                val castleRules : CastleRule = CastleRule.all,
                val enPassant : Option[Square] = None,
                val activeKing : Option[Square] = None,
                val inactiveKing : Option[Square] = None) extends Node {

  /**
   * The moves available from this position
   */

  lazy val moves : List[Move] = new MoveGenerator(this).generateAll

  /**
   * Squares attacked by the other player.
   */

  lazy val inactiveAttacks : List[Move] = new MoveGenerator(this as !active).generateAttacks

  /**
   * Static evaluation of this position.
   */

  lazy val eval : Double = BasicEvaluation.evaluate(this)

  /**
   * The `FEN` for this position.
   */

  lazy val FEN : FEN = ???

  /**
   * Gets this chess node as a new color.
   * @param color a color.
   * @return a new node with `color` to move.
   */

  override def as(color : Color) : BasicNode =
    if(color == active) this
    else new BasicNode(pieces, fullMove, halfMove, color, castleRules, None, inactiveKing, activeKing)

  /**
   * Gets the piece at the given square or `None` if empty or off the board.
   * @param square a square.
   * @return a piece or `None`.
   */

  def apply(square : Square) : Option[Piece] = pieces(square.toInt)

  /**
   * Makes the given move.
   * @param move a move.
   * @return a node or `None`.
   */

  override def move(move : Move) = {
    val buffer = pieces.toBuffer
    move match {
      case PawnPromote(_,_,p) => buffer.update(move.target.toInt, Some(p))
      case PawnPromoteCapture(_,_,_,p) => buffer.update(move.target.toInt, Some(p))
      case _ => buffer.update(move.target.toInt, apply(move.origin))
    }
    buffer.update(move.origin.toInt, None)
    move match {
      case KingCastleShort(_) =>
        buffer.update(move.target.toInt-1, Some(Rook(active)))
        buffer.update(move.target.toInt+1, None)
      case KingCastleLong(_) =>
        buffer.update(move.target.toInt+1, Some(Rook(active)))
        buffer.update(move.target.toInt-2, None)
      case _ =>
    }
    move match {
      case PawnEPCapture(_,_) =>
        buffer.update(move.target.toInt-active.toInt*8, None)
      case _ =>
    }
    val hMove = move match {
      case PawnAdvance(_,_) => 0
      case PawnCapture(_,_,_) => 0
      case PawnEPCapture(_,_) => 0
      case PawnPromote(_,_,_) => 0
      case PawnPromoteCapture(_,_,_,_) => 0
      case _ => halfMove+1
    }
    val cRule = move match {
      case KingCastleLong(_) => castleRules - KingCastleLong(active) - KingCastleShort(active)
      case KingCastleShort(_) => castleRules - KingCastleShort(active) - KingCastleLong(active)
      case KingMove(_,_) => castleRules - KingCastleShort(active) - KingCastleLong(active)
      case KingCapture(_,_,_) => castleRules - KingCastleShort(active) - KingCastleLong(active)
      case RookMove(o,_) =>
        if(o.toInt == Rank.back(active).toInt*8)
          castleRules - KingCastleLong(active)
        else if(o.toInt == Rank.back(active).toInt*8+File.H.toInt)
          castleRules - KingCastleShort(active)
        else castleRules
      case RookCapture(o,_,_) =>
        if(o.toInt == Rank.back(active).toInt*8)
          castleRules - KingCastleLong(active)
        else if(o.toInt == Rank.back(active).toInt*8+File.H.toInt)
          castleRules - KingCastleShort(active)
        else castleRules
      case _ => castleRules
    }
    val ep = move match {
      case PawnAdvance(from,to) =>
        if(math.abs(from.toInt-to.toInt) == 16) Some( Square(from.toInt+8*active.toInt ))
        else None
      case _ => None
    }
    val fMove = active match {
      case Black => fullMove+1
      case _ => fullMove
    }
    val aKing = move match {
      case KingCastleLong(_) => Some(move.target)
      case KingCastleShort(_) => Some(move.target)
      case KingMove(_,_) => Some(move.target)
      case KingCapture(_,_,_) => Some(move.target)
      case _ => activeKing
    }
    new BasicNode(buffer.toList, fMove, hMove, !active, cRule, ep, inactiveKing, aKing)
  }

  /**
   * Makes a verbose string of this chess position.
   * @return a verbose string.
   */

  def verboseString = {
    val builder = StringBuilder.newBuilder
    builder ++= NodeStringBuilder.mkString(this)
    builder += '\n'
    builder ++= s"$active [${BasicEvaluation.evaluate(this)}] fullMove=$fullMove " +
      s"halfMove=$halfMove castle=$castleRules enPassant=${enPassant.getOrElse('-')}\n" +
      s"activeKing=$activeKing inactiveKing=$inactiveKing"
  }

  /**
   * Makes a string representation of this chess position.
   * @return a string representation of the board.
   */

  override def toString = NodeStringBuilder.mkString(this)
}

/**
 * `BasicNode` utilities and shared values.
 */

object BasicNode {

  /**
   * Gets a new node builder.
   * @return a node builder object.
   */

  def newBuilder = NodeBuilder.newInstance

  /**
   * The starting position.
   */

  val startingPosition = {
    val builder = newBuilder
    builder += Rook(Black)
    builder += Knight(Black)
    builder += Bishop(Black)
    builder += Queen(Black)
    builder += King(Black)
    builder += Bishop(Black)
    builder += Knight(Black)
    builder += Rook(Black)
    for(i <- 1 to 8) {
      builder += Pawn(Black)
    }
    for(i <- 1 to 32) {
      builder += None
    }
    for(i <- 1 to 8) {
      builder += Pawn(White)
    }
    builder += Rook(White)
    builder += Knight(White)
    builder += Bishop(White)
    builder += Queen(White)
    builder += King(White)
    builder += Bishop(White)
    builder += Knight(White)
    builder += Rook(White)
    builder.result()
  }

  /**
   * The empty board.
   */
  
  val empty = newBuilder.result()
}
