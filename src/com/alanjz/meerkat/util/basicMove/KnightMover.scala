package com.alanjz.meerkat.util.basicMove

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move.{KnightCapture, KnightMove}
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.{File, Rank, Square}

class KnightMover(val node : BasicNode) extends Mover {

  def knightMoves(from : Square) : List[Move] = {

    val f = from.file
    val r = from.rank

    val board = node.pieces
    val moves = List.newBuilder[Move]

    def get(f : Boolean, i : Int) =
      if(f) {
        val to = from.toInt+i
        val piece = board(to)

        if(piece.isEmpty) {
          moves += new KnightMove(from, Square(to))
        }
        else if(piece.get.color != node.active) {
          moves += new KnightCapture(from, Square(to), piece.get)
        }
      }

    // 1:00
    get(r.toInt < Rank._7.toInt && f.toInt < File.H.toInt, 17)

    // 2:00
    get(r.toInt < Rank._8.toInt && f.toInt < File.G.toInt, 10)

    // 4:00
    get(r.toInt > Rank._1.toInt && f.toInt < File.G.toInt, -6)

    // 5:00
    get(r.toInt > Rank._2.toInt && f.toInt < File.H.toInt, -15)

    // 7:00
    get(r.toInt > Rank._2.toInt && f.toInt > File.A.toInt, -17)

    // 8:00
    get(r.toInt > Rank._1.toInt && f.toInt > File.B.toInt, -10)

    // 10:00
    get(r.toInt < Rank._8.toInt && f.toInt > File.B.toInt, 6)

    // 11:00
    get(r.toInt < Rank._7.toInt && f.toInt > File.A.toInt, 15)

    moves.result()
  }
}

object KnightMover {

}
