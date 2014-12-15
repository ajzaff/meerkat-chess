package com.alanjz.meerkat.util.position

import com.alanjz.meerkat.util.position.FENBuilder.FEN
import com.alanjz.meerkat.position.mutable.Node

class FENBuilder(node : Node) {
  def mkFEN : FEN = ???
}

object FENBuilder {
  type FEN = String
}