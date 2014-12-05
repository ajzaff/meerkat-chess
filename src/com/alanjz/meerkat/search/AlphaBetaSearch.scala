package com.alanjz.meerkat.search

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces.Color.White
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.basicEval.BasicEvaluation
import com.alanjz.meerkat.util.move.order.CaptureValue

class AlphaBetaSearch(ply : Int) extends Search[BasicNode] {

  /**
   * Perform a search on the given node.
   * @param node a node.
   * @return a best move.
   */
  override def search(node: BasicNode): BestMove = {
    val moves = node.moves.sortBy(CaptureValue.comparator(_))
    if(node.active == White) {
      val ms = moves.map(m => (m,-alphaBeta(node.move(m), Double.MinValue, Double.MaxValue, ply-1)))
      println(ms.mkString("\n"))
      ms.maxBy(_._2)
    }
    else {
      val ms = moves.map(m => (m,-alphaBeta(node.move(m), -Double.MaxValue, -Double.MinValue, ply-1)))
      println(ms.mkString("\n"))
      ms.maxBy(_._2)
    }
  }

  private def alphaBeta(node : BasicNode, alpha : Double, beta : Double, depthLeft : Int ) : Double = {
    if( depthLeft == 0 ) return quiesce( node, alpha, beta )
    val moves = node.moves.sortBy(CaptureValue.comparator)
    var a = alpha
    for (move <- moves)  {
      val score = -alphaBeta(node.move(move), -beta, -a, depthLeft - 1 )
      if( score >= beta )
        return beta;   //  fail hard beta-cutoff
      if( score > a )
        a = score
    }
    a
  }

  private def quiesce(node : BasicNode, alpha : Double, beta : Double) : Double = {
    val stand_pat = node.eval
    var a = alpha
    if( stand_pat >= beta )
      return beta
    if( a < stand_pat )
      a = stand_pat

    val captures = node.moves.filter(m => m.isInstanceOf[Move.Capture])

    //println(s"${captures.size} capture(s) available.")
    //println(node.verboseString)
    //println(s"alpha=$a beta=$beta")

    for(capture <- captures) {

      //println(s"searching $capture")

      val score = -quiesce(node.move(capture), -beta, -a )

      if( score >= beta )
        return beta
      if( score > a )
        a = score
    }
    a
  }
}
