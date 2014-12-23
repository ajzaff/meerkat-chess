package com.alanjz.meerkat.util.position.mutable

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.Square.H1
import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.iterator.square.FENSquareIterator
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

object NodeStringBuilder {
  def mkString(node : MaskNode) : String = {
    val array = Array.fill[Char](64) (' ')

    def process(mask : BitMask, c : Char): Unit = {
      var pieces = mask
      while(pieces != BitMask.empty) {
        val p = BitMask.bitScanForward(pieces)
        array(p) = c
        pieces &= (pieces-1)
      }
    }

    process(node.whitePawns, 'P')
    process(node.whiteKnights, 'N')
    process(node.whiteBishops, 'B')
    process(node.whiteRooks, 'R')
    process(node.whiteQueens, 'Q')
    process(node.whiteKing, 'K')
    process(node.blackPawns, 'p')
    process(node.blackKnights, 'n')
    process(node.blackBishops, 'b')
    process(node.blackRooks, 'r')
    process(node.blackQueens, 'q')
    process(node.blackKing, 'k')

    val builder = StringBuilder.newBuilder
    val iter = FENSquareIterator.newInstance
    builder ++= " _______________________________\n|"
    for(i <- iter) {
      builder ++= s" ${array(i.toInt)} |"
      if((i.toInt+1) % 8 == 0) {
        builder ++= "\n|___|___|___|___|___|___|___|___| \n"
        if(i != H1) builder += '|'
      }
    }
    builder.mkString
  }
}
