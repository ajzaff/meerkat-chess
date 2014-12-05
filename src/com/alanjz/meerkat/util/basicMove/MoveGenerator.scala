package com.alanjz.meerkat.util.basicMove

import com.alanjz.meerkat.Square.A1
import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.{Rank, Square}

class MoveGenerator(val node : BasicNode) {


  def generateAttacks : List[Move] = {
    val active = node.active
    val home = Rank.home(active)
    val enPassantSquare = node.enPassant
    val pd = node.active : Int
    val movesBuilder = List.newBuilder[Move]


    for(i <- A1.toInt to Square.H8.toInt) {
      val piece = node(Square(i))
      if(piece.isDefined && piece.get.color == active) {
        val moves =
          piece.get match {
            case Pawn(_) => new PawnMover(node).pawnAttacks(Square(i))
            case Knight(_) => new KnightMover(node).knightMoves(Square(i))
            case Bishop(_) => new BishopMover(node).apply(Square(i))
            case Rook(_) => new RookMover(node).rookMoves(Square(i))
            case Queen(_) => new QueenMover(node).queenMoves(Square(i))
            case King(_) => new KingMover(node).kingAttacks(Square(i))
            case _ => Nil
          }
        movesBuilder ++= moves
      }
    }

    movesBuilder.result()
  }

  def generateAll : List[Move] = {
    val active = node.active
    val home = Rank.home(active)
    val enPassantSquare = node.enPassant
    val pd = node.active : Int
    val movesBuilder = List.newBuilder[Move]


    for(i <- A1.toInt to Square.H8.toInt) {
      val piece = node(Square(i))
      if(piece.isDefined && piece.get.color == active) {
        val moves =
          piece.get match {
            case Pawn(_) => new PawnMover(node).pawnMoves(Square(i))
            case Knight(_) => new KnightMover(node).knightMoves(Square(i))
            case Bishop(_) => new BishopMover(node).apply(Square(i))
            case Rook(_) => new RookMover(node).rookMoves(Square(i))
            case Queen(_) => new QueenMover(node).queenMoves(Square(i))
            case King(_) => new KingMover(node).kingMoves(Square(i))
            case _ => Nil
          }
        for (move <- moves)
          if(! new CheckChecker(node.move(move) as node.active).inCheck)
            movesBuilder += move
      }
    }

    movesBuilder.result()
  }
}
