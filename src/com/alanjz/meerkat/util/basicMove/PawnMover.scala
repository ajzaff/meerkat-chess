package com.alanjz.meerkat.util.basicMove

import com.alanjz.meerkat.Square.{A1, H8}
import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move._
import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.{File, Rank, Square}

class PawnMover(val node : BasicNode) extends Mover {

  val movesBuilder = List.newBuilder[Move]
  private val pd = node.active.toInt
  private val active = node.active
  private val home = Rank.home(active)


  def pawnAttacks(from : Square) : List[Move] = {
    val pd = node.active.toInt
    val left = from.toInt + 7*pd
    val right = from.toInt + 9*pd
    if(left <= H8.toInt && left >= A1.toInt ) {
      movesBuilder += new PawnCapture(from, Square(left), null)
    }
    if(right <= H8.toInt && right >= A1.toInt ) {
      movesBuilder += new PawnCapture(from, Square(right), null)
    }
    movesBuilder.result()
  }

  def pawnMoves(from : Square) : List[Move] = {

    def to1 = from.toInt+8*pd
    def to2 = from.toInt+16*pd

    if ( to1 >= A1.toInt && to1 <= H8.toInt &&   node(Square(to1)).isEmpty) {

      if(Square(to1).rank == Rank.promote(active)) {
        movesBuilder += new PawnPromote(from, Square(to1), Queen(active))
        movesBuilder += new PawnPromote(from, Square(to1), Rook(active))
        movesBuilder += new PawnPromote(from, Square(to1), Bishop(active))
        movesBuilder += new PawnPromote(from, Square(to1), Knight(active))
      }
      else {
        movesBuilder += new PawnAdvance(from, Square(to1))
        if (from.rank == home && node(Square(to2)).isEmpty) {
          movesBuilder += new PawnAdvance(from, Square(to2))
        }
      }
    }

    enPassantCaptures(from)
    pawnCaptures(from)
    movesBuilder.result()
  }


  def enPassantCaptures(from : Square) : Unit = {

    val enPassantSquare = node.enPassant

    if (!enPassantSquare.isDefined) {
      return
    }

    val ep = enPassantSquare.get.toInt

    // en passant captures (left)
    if ((active == White && from.file != File.A) ||
      (active == Black && from.file != File.H)) {
      if(ep == from.toInt+7*pd) {
        movesBuilder += new PawnEPCapture(from, Square(ep))
      }
    }

    // en passant captures (right)
    if ((active == White && from.file != File.H) ||
      (active == Black && from.file != File.A)) {
      if(ep == from.toInt+9*pd) {
        movesBuilder += new PawnEPCapture(from, Square(ep))
      }
    }
  }

  def pawnCaptures(from: Square): Unit = {

    // pawn capture (left)
    if ((active == White && from.file != File.A) ||
      (active == Black && from.file != File.H)) {
      val to = from.toInt+7*pd
      val p = node(Square(to))
      if (p.isDefined && p.get.color != active) {
        if(Square(to).rank == Rank.promote(active)) {
          movesBuilder += new PawnPromoteCapture(from, Square(to), p.get, Queen(active))
          movesBuilder += new PawnPromoteCapture(from, Square(to), p.get, Rook(active))
          movesBuilder += new PawnPromoteCapture(from, Square(to), p.get, Knight(active))
          movesBuilder += new PawnPromoteCapture(from, Square(to), p.get, Bishop(active))
        }
        else {
          movesBuilder += new PawnCapture(from, Square(to), p.get)
        }
      }
    }

    // pawn capture (right)
    if ((active == White && from.file != File.H) ||
      (active == Black && from.file != File.A)) {
      val to = from.toInt+9*pd
      val p = node(Square(to))
      if (p.isDefined && p.get.color != active) {

        if(Square(to).rank == Rank.promote(active)) {
          movesBuilder += new PawnPromoteCapture(from, Square(to), p.get, Queen(active))
          movesBuilder += new PawnPromoteCapture(from, Square(to), p.get, Rook(active))
          movesBuilder += new PawnPromoteCapture(from, Square(to), p.get, Knight(active))
          movesBuilder += new PawnPromoteCapture(from, Square(to), p.get, Bishop(active))
        }
        else {
          movesBuilder += new PawnCapture(from, Square(to), p.get)
        }


      }
    }
  }
}

object PawnMover {

}