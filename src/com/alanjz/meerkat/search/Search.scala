package com.alanjz.meerkat.search

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.position.mutable.Node

/**
 * The base class of all search algorithms.
 * @tparam N
 */

trait Search[N <: Node] {

  type BestMove = (Move, Double)

  /**
   * Perform a search on the given node.
   * @param node a node.
   * @return a best move.
   */

  def search(node : N) : BestMove
}