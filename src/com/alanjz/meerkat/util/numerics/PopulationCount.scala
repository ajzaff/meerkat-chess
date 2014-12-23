package com.alanjz.meerkat.util.numerics

import com.alanjz.meerkat.util.numerics.BitMask.BitMask

/**
 * Created by alan on 12/16/14.
 */

sealed class PopulationCount {
  val popCountOfByte256 = Array.ofDim[Int](256)
  popCountOfByte256(0) = 0
  for (i <- 1 to 255)
    popCountOfByte256(i) = popCountOfByte256(i/2) + (i & 1)


  def count(x : BitMask) : Int =
    popCountOfByte256((x         & 0xff).toInt) +
    popCountOfByte256(((x >>  8) & 0xff).toInt) +
    popCountOfByte256(((x >> 16) & 0xff).toInt) +
    popCountOfByte256(((x >> 24) & 0xff).toInt) +
    popCountOfByte256(((x >> 32) & 0xff).toInt) +
    popCountOfByte256(((x >> 40) & 0xff).toInt) +
    popCountOfByte256(((x >> 48) & 0xff).toInt) +
    popCountOfByte256( (x >> 56).toInt)
}

object PopulationCount {
  val instance = new PopulationCount
}
