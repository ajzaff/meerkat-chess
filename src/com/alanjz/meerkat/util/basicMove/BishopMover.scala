package com.alanjz.meerkat.util.basicMove

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.moves.Move.{BishopCapture, BishopMove}
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.iterator.RaySquareIterator
import com.alanjz.meerkat.util.iterator.RaySquareIterator._

/**
 * Generates bishop moves
 * @param node
 */

class BishopMover(val node : BasicNode) extends Mover {

  /**
   * Generates bishop moves originating from the given square.
   * @param origin a square.
   * @return a list of moves.
   */

  def apply(origin : Square) : List[Move] = {

    val moves = List.newBuilder[Move]

    def traceDirection(order : Order): Unit = {
      val iter = RaySquareIterator.newInstance(origin, order)
      var running = true
      while(iter.hasNext && running) {
        val target = iter.next
        val piece = node(target)
        if(piece.isDefined) {
          if(piece.get.color != node.active) {
            moves += BishopCapture(origin, target, piece.get)
          }
          running = false
        }
        else {
          moves += BishopMove(origin, target)
        }
      }
    }

    traceDirection(Northeast)
    traceDirection(Southeast)
    traceDirection(Southwest)
    traceDirection(Northwest)

    moves.result()
  }
}

object BishopMover {

}