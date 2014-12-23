package com.alanjz.meerkat.moves

import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

class PseudoLegalMover(node : MaskNode) {

  def getMoves : List[Move] =
    new PawnMover(node).mkList ++
      new KnightMover(node).mkList ++
      new BishopMover(node).mkList ++
      new RookMover(node).mkList ++
      new QueenMover(node).mkList ++
      new KingMover(node).mkList
}