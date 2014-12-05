package com.alanjz.meerkat.search

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.position.BasicNode

/**
 * The base class of all search algorithms.
 * @tparam N
 */

trait Search[N <: BasicNode] {

  type BestMove = (Move, Double)

  /**
   * Perform a search on the given node.
   * @param node a node.
   * @return a best move.
   */

  def search(node : N) : BestMove
}