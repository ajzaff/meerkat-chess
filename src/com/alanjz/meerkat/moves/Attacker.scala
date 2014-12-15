package com.alanjz.meerkat.moves

import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

class Attacker(node : MaskNode) {

  def getAttackers(mask : BitMask): BitMask = {

    // The attackers mask.
    var attackers = BitMask.empty

    // Get enemy pieces.
    val inactivePawns = node.inactivePawns
    val inactiveKnights = node.inactiveKnights
    val inactiveBishops = node.inactiveBishops
    val inactiveRooks = node.inactiveRooks
    val inactiveQueens = node.inactiveQueens
    val inactiveKing = node.inactiveKing

    // Get movers.
    val pawnMover = new PawnMover(node)
    val knightMover = new KnightMover(node)
    val bishopMover = new BishopMover(node)
    val rookMover = new RookMover(node)
    val queenMover = new QueenMover(node)
    val kingMover = new KingMover(node)

    // Traces attacks back to enemy pieces.
    attackers |= pawnMover.getAttacks(mask) & inactivePawns
    attackers |= knightMover.getAttacks(mask) & inactiveKnights
    attackers |= bishopMover.getAttacks(mask) & inactiveBishops
    attackers |= rookMover.getAttacks(mask) & inactiveRooks
    attackers |= queenMover.getAttacks(mask) & inactiveQueens
    attackers |= kingMover.getAttacks(mask) & inactiveKing

    // Return the attackers.
    attackers
  }
}
