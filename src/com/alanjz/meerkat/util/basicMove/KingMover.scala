package com.alanjz.meerkat.util.basicMove

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move.{KingCapture, KingCastleLong, KingCastleShort, KingMove}
import com.alanjz.meerkat.pieces.Piece.{King, Rook}
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.{File, Rank, Square}

class KingMover(val node : BasicNode) extends Mover {

  private def squaresAttacked(squares : Square*) = {
    node.inactiveAttacks.exists(squares contains _.target)
  }

  def kingCastling(from : Square) : List[Move] = {

    val moves = List.newBuilder[Move]
    val active = node.active
    val canCastleShort = node.castleRules.contains(KingCastleShort(active))
    val canCastleLong = node.castleRules.contains(KingCastleLong(active))
    val kingSquare = Square(Rank.back(active).toInt * 8 + File.E.toInt)
    val rookHSquare = Square(Rank.back(active).toInt * 8 + File.H.toInt)
    val rookASquare = Square(Rank.back(active).toInt * 8 + File.A.toInt)
    val kingOnSquare = node(kingSquare).isDefined && node(kingSquare).get == King(active)
    val rookOnH = node(rookHSquare).isDefined && node(rookHSquare).get == Rook(active)
    val rookOnA =node(rookASquare).isDefined && node(rookASquare).get == Rook(active)

    val sqB = Square(Rank.back(active).toInt * 8 + File.B.toInt)
    val sqC = Square(Rank.back(active).toInt * 8 + File.C.toInt)
    val sqD = Square(Rank.back(active).toInt * 8 + File.D.toInt)
    val sqE = Square(Rank.back(active).toInt * 8 + File.E.toInt)
    val sqF = Square(Rank.back(active).toInt * 8 + File.F.toInt)
    val sqG = Square(Rank.back(active).toInt * 8 + File.G.toInt)

    def isEmpty(squares : Square*) = squares.forall(s => node(s).isEmpty)

    // castling short
    if(canCastleShort && kingOnSquare && rookOnH && isEmpty(sqF,sqG) && ! squaresAttacked(sqE,sqF,sqG)) {
      moves += KingCastleShort(active)
    }

    // castling long
    if(canCastleLong && kingOnSquare && rookOnA && isEmpty(sqD,sqC,sqB) && ! squaresAttacked(sqE,sqD,sqC)) {
      moves += KingCastleLong(active)
    }

    moves.result()
  }


  def kingAttacks(from : Square): List[Move] = {

    val board = node.pieces
    val moves = List.newBuilder[Move]

    def get(f : Boolean, i : Int) = {
      if(f) {
        val piece = board(from.toInt+i)
        if(piece.isEmpty) {
          moves += KingMove(from,Square(from.toInt+i))
        }
        else if(piece.get.color != node.active) {
          moves += KingCapture(from,Square(from.toInt+i),piece.get)
        }
      }
    }

    // 1:30
    get(from.rank.toInt < Rank._8.toInt && from.file.toInt < File.H.toInt,9)
    // 3:00
    get(from.file.toInt < File.H.toInt,1)
    // 4:30
    get(from.rank.toInt > Rank._1.toInt && from.file.toInt < File.H.toInt,-7)
    // 6:00
    get(from.rank.toInt > Rank._1.toInt,-8)
    // 7:30
    get(from.rank.toInt > Rank._1.toInt && from.file.toInt > File.A.toInt,-9)
    // 9:00
    get(from.file.toInt > File.A.toInt,-1)
    // 10:30
    get(from.rank.toInt < Rank._8.toInt && from.file.toInt > File.A.toInt,7)
    // 12:00
    get(from.rank.toInt < Rank._8.toInt,8)

    moves.result()
  }

  def kingMoves(from : Square): List[Move] = {

    val board = node.pieces
    val moves = List.newBuilder[Move]

    def get(f : Boolean, i : Int) = {
      if(f) {
        val piece = board(from.toInt+i)
        if(piece.isEmpty) {
          moves += KingMove(from,Square(from.toInt+i))
        }
        else if(piece.get.color != node.active) {
          moves += KingCapture(from,Square(from.toInt+i),piece.get)
        }
      }
    }

    // do castling
    moves ++= kingCastling(from)

    // 1:30
    get(from.rank.toInt < Rank._8.toInt && from.file.toInt < File.H.toInt,9)
    // 3:00
    get(from.file.toInt < File.H.toInt,1)
    // 4:30
    get(from.rank.toInt > Rank._1.toInt && from.file.toInt < File.H.toInt,-7)
    // 6:00
    get(from.rank.toInt > Rank._1.toInt,-8)
    // 7:30
    get(from.rank.toInt > Rank._1.toInt && from.file.toInt > File.A.toInt,-9)
    // 9:00
    get(from.file.toInt > File.A.toInt,-1)
    // 10:30
    get(from.rank.toInt < Rank._8.toInt && from.file.toInt > File.A.toInt,7)
    // 12:00
    get(from.rank.toInt < Rank._8.toInt,8)

    moves.result()
  }
}

object KingMover {

}