package com.alanjz.meerkat.util.basicMove

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move.{QueenCapture, QueenMove}
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.iterator.RaySquareIterator
import com.alanjz.meerkat.util.iterator.RaySquareIterator._

class QueenMover(val node : BasicNode) extends Mover {

  def queenMoves(origin : Square): List[Move]= {

    val moves = List.newBuilder[Move]

    def traceDirection(order : Order): Unit = {
      val iter = RaySquareIterator.newInstance(origin, order)
      var free = true
      while(iter.hasNext && free) {
        val target = iter.next
        val piece = node(target)
        if(piece.isDefined) {
          if(piece.get.color != node.active) {
            moves += new QueenCapture(origin, target, piece.get)
          }
          free = false
        }
        else {
          moves += new QueenMove(origin, target)
        }
      }
    }

    traceDirection(North)
    traceDirection(Northeast)
    traceDirection(East)
    traceDirection(Southeast)
    traceDirection(South)
    traceDirection(Southwest)
    traceDirection(West)
    traceDirection(Northwest)

    moves.result()
  }
}

object QueenMover {

}
