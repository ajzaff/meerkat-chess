package com.alanjz.meerkat.search

import com.alanjz.meerkat.evaluation.MaterialEvaluation
import com.alanjz.meerkat.moves.{PseudoLegalMover, Move}
import com.alanjz.meerkat.position.mutable.MaskNode

class IterativeSearch(node : MaskNode, maxDepth : Int) {

  class BestMove(var move : Move, var score : Double) {
    def this() = this(null, Double.MinValue)
    def or(move : BestMove) = {
      if (move.score > score) {
        score = move.score
        this.move = move.move
      }
      this
    }
    implicit def unary_- : BestMove = new BestMove(move, -score)
  }

  def search : BestMove = {
    val best = new BestMove
    for(i <- 1 to maxDepth) {
      best or search(i)
    }
    best
  }

  def search(depth : Int) : BestMove = {
    val pseudos = new PseudoLegalMover(node).getMoves
    val best = new BestMove

    for(move <- pseudos) {
      node.make(move)
      best or new BestMove(move, search(depth-1, Double.MinValue, Double.MaxValue))
    }
    best
  }

  def search(depth : Int, alpha : Double, beta : Double): Double = {
    var best = alpha

    if(depth == 0) {
      return -quiesce(-beta, -alpha)
    }
    if(node.isTerminal) {
      return alpha
    }

    val pseudos = new PseudoLegalMover(node).getMoves

    for(move <- pseudos) {
      node.make(move)
      val result = -search(depth-1, -beta, -alpha)
      if(result > alpha) {
        best = result
      }
      if(best >= beta) {
        return beta
      }
    }
    best
  }

  private def quiesce(alpha : Double, beta : Double) : Double = {

    val standPat = MaterialEvaluation.evaluate(node)
    var a = alpha

    if(standPat >= beta) {
      return beta
    }

    if(a < standPat) {
      a = standPat
    }

    val captures = new PseudoLegalMover(node).getMoves.filter(m => m.isInstanceOf[Move.Capture])

    for(capture <- captures) {

      node.make(capture)
      val score = -quiesce(-beta, -a)

      if( score >= beta )
        return beta
      if( score > a )
        a = score
    }
    a
  }
}