package com.alanjz.meerkat.util.basicMove

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move.{RookCapture, RookMove}
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.iterator.RaySquareIterator
import com.alanjz.meerkat.util.iterator.RaySquareIterator._

class RookMover(val node : BasicNode) extends Mover  {

  def rookMoves(origin : Square) : List[Move] = {

    val moves = List.newBuilder[Move]

    def traceDirection(order : Order): Unit = {
      val iter = RaySquareIterator.newInstance(origin, order)
      var free = true
      while(iter.hasNext && free) {
        val target = iter.next
        val piece = node(target)
        if(piece.isDefined) {
          if(piece.get.color != node.active) {
            moves += new RookCapture(origin, target, piece.get)
          }
          free = false
        }
        else {
          moves += new RookMove(origin, target)
        }
      }
    }

    traceDirection(North)
    traceDirection(East)
    traceDirection(South)
    traceDirection(West)

    moves.result()
  }
}

object RookMover {

}