package com.alanjz.meerkat.util.basicEval

import com.alanjz.meerkat.moves.Move.{KingCastleLong, KingCastleShort}
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.position.BasicNode

/**
 * Created by alan on 12/5/14.
 */
object CastlingFilter extends Evaluation {

  def evaluate(node : BasicNode) : Double = {

    if(node.activeCastled) {
      return 0
    }

    val c = node.castleRules
    val K = KingCastleShort(White)
    val k = KingCastleShort(Black)
    val Q = KingCastleLong(White)
    val q = KingCastleLong(Black)
    val score =
      node.active match {
        case White => if(!(c.contains(K) || c.contains(Q))) {
          -0.3
        }
        else {
          0
        }
        case Black => if(!(c.contains(k) || c.contains(q))) {
          -0.3
        }
        else {
          0
        }
      }
    score
  }
}