package com.alanjz.meerkat.util.hashing

import scala.util.Random

/**
 * Created by alan on 12/16/14.
 */
class ZobristHash(seed : Long) {
  val N = 768
  val randomKeys = Array.ofDim[Long](N)

  Random.setSeed(seed)
  for(i <- 0 to N-1) randomKeys update (i, Random.nextLong())

  for(i <- 0 to N-1) println(randomKeys(i))
}

object ZobristHash {
  type ZHash = Long

  val instance = new ZobristHash(27070365l)
}