package com.alanjz.meerkat.util

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.moves.CastleRule
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.pieces.{Color, Piece}
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.iterator.FENSquareIterator

/**
 * Builds a node from piece mappings.
 */

class NodeBuilder {

  /**
   * The node variable backed by this builder.
   */

  private var node = new BasicNode()

  /**
   * The FEN iterator associated with this builder.
   */

  private val squares = FENSquareIterator.newInstance

  /**
   * Sets the piece at the given square.
   * @param square the square.
   * @param piece a piece.
   */

  def putPiece(square : Square, piece : Piece): Unit = {
    val activeKing = node.active match {
      case White => if(piece == King(White)) Some(square) else node.activeKing
      case Black => if(piece == King(Black)) Some(square) else node.activeKing
    }
    val inactiveKing = node.active match {
      case White => if(piece == King(Black)) Some(square) else node.inactiveKing
      case Black => if(piece == King(White)) Some(square) else node.inactiveKing
    }
    node = new BasicNode(
      node.pieces.updated(square.toInt, Some(piece)), node.fullMove, node.halfMove,
      node.active, node.castleRules, node.enPassant, activeKing, inactiveKing
    )
  }

  /**
   * Clears the piece from the given square.
   * @param square the square.
   */

  def clearPiece(square : Square): Unit = {
    val activeKing =
      if(square == node.activeKing.getOrElse(None)) None else node.activeKing
    val inactiveKing =
      if(square == node.inactiveKing.getOrElse(None)) None else node.inactiveKing
    node = new BasicNode(
      node.pieces.updated(square.toInt, None), node.fullMove, node.halfMove,
      node.active, node.castleRules, node.enPassant, activeKing, inactiveKing
    )
  }

  /**
   * Adds a piece at a location determined by the internal FEN iterator.
   * @param piece a piece.
   */

  def +=(piece : Piece): Unit = +=( Some(piece) )

  /**
   * Adds a piece at a location determined by the internal FEN iterator.
   * @param piece a piece or `None`.
   */

  def +=(piece : Option[Piece]): Unit = {
    val square = squares.next
    val activeKing = node.active match {
      case White => if(piece.getOrElse(None) == King(White)) Some(square) else node.activeKing
      case Black => if(piece.getOrElse(None) == King(Black)) Some(square) else node.activeKing
    }
    val inactiveKing = node.active match {
      case White => if(piece.getOrElse(None) == King(Black)) Some(square) else node.inactiveKing
      case Black => if(piece.getOrElse(None) == King(White)) Some(square) else node.inactiveKing
    }
    node = new BasicNode(
      node.pieces.updated(square.toInt, piece), node.fullMove, node.halfMove,
      node.active, node.castleRules, node.enPassant, activeKing, inactiveKing
    )
  }

  /**
   * Sets the castle rules.
   * @param castling castle rules.
   */

  def setCastling(castling : CastleRule) {
    node = new BasicNode(
      node.pieces, node.fullMove, node.halfMove,
      node.active, castling, node.enPassant, node.activeKing, node.inactiveKing)
  }

  /**
   * Sets the active color.
   * @param color a color.
   */

  def setActive(color : Color): Unit = {
    val activeKing =
      if(color == node.active) node.activeKing else node.inactiveKing
    val inactiveKing =
      if(color == node.active) node.inactiveKing else node.activeKing
    node = new BasicNode(
      node.pieces, node.fullMove, node.halfMove,
      color, node.castleRules, node.enPassant, activeKing, inactiveKing)
  }

  /**
   * Sets the full-move number.
   * @param fullMove number of full moves.
   */
  
  def setFullMove(fullMove : Int): Unit = {
    node = new BasicNode(
      node.pieces, fullMove, node.halfMove,
      node.active, node.castleRules, node.enPassant, node.activeKing, node.inactiveKing)
  }

  /**
   * Sets the half-move counter.
   * @param halfMove number of half moves.
   */

  def setHalfMove(halfMove : Int): Unit = {
    node = new BasicNode(
      node.pieces, node.fullMove, halfMove,
      node.active, node.castleRules, node.enPassant, node.activeKing, node.inactiveKing)
  }

  /**
   * Sets the en passant square, if any.
   * @param square a square or `None`.
   */

  def setEnPassantSquare(square : Option[Square]): Unit = {
    node = new BasicNode(
      node.pieces, node.fullMove, node.halfMove,
      node.active, node.castleRules, square, node.activeKing, node.inactiveKing)
  }

  /**
   * Sets the en passant square.
   * @param square a square.
   */

  def setEnPassantSquare(square : Square) : Unit = setEnPassantSquare(Some(square))

  /**
   * Builds the node.
   * @return a node.
   */

  def result() : BasicNode = node
}

object NodeBuilder {

  /**
   * Gets a new `NodeBuilder` instance.
   * @return a new `NodeBuilder`.
   */

  def newInstance = new NodeBuilder
}