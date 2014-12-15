package com.alanjz.meerkat.moves

import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

class Attacker(node : MaskNode) {

  def getAttacks(mask : BitMask): BitMask = {

    // The checkers mask.
    var checkers = BitMask.empty

    // Get enemy pieces.
    val inactivePawns = node.inactivePawns

    // Get movers.
    val pawnMover = new PawnMover(node)

    // Traces attacks back to enemy pawns.
    checkers |= pawnMover.getAttacks(mask) & inactivePawns

    checkers
  }
}
