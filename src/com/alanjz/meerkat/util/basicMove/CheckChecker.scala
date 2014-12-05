package com.alanjz.meerkat.util.basicMove

import com.alanjz.meerkat.position.BasicNode

class CheckChecker(node : BasicNode) {
  def inCheck : Boolean = {
    if(node.activeKing.isEmpty) return false
    node.inactiveAttacks.exists(_.target == node.activeKing.get)
  }
}