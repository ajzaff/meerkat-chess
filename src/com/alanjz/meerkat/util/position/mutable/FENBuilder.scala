package com.alanjz.meerkat.util.position.mutable

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.iterator.square.FENSquareIterator
import com.alanjz.meerkat.util.position.mutable.FENBuilder.FEN

class FENBuilder(node : MaskNode) {
  def mkFEN : FEN = {
    val builder =StringBuilder.newBuilder
    val fenIterator = FENSquareIterator.newInstance
    var spaces = 0
    for(i <- fenIterator) {
      val piece = node.at(i.toInt)
      if(piece.isEmpty) {
        spaces += 1
      }
      else {
        if(spaces > 0) {
          builder += spaces.toString.head
        }
        spaces = 0
        builder += piece.get.toChar
      }
      if((i.toInt+1) % 8 == 0 && i != Square.H1) {
        if(spaces > 0) {
          builder += spaces.toString.head
        }
        spaces = 0
        builder += '/'
      }
    }
    if(spaces > 0) {
      builder += spaces.toString.head
    }
    builder ++= s" ${node.details}"
    builder.mkString
  }
}

object FENBuilder {
  type FEN = String
}