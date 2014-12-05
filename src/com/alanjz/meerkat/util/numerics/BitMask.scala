package com.alanjz.meerkat.util.numerics

object BitMask {

  type BitMask = Long

  val all = -1l
  val none = 0l

  def mkString(mask : BitMask) = {
    val builder = StringBuilder.newBuilder
    for (i <- 7 to 0 by -1) {
      for (j <- 0 to 7) {
        if ((mask & (1l << (8 * i + j))) != 0) {
          builder += '1'
        }
        else {
          builder += '0'
        }
      }
     builder += '\n'
    }
    builder.mkString
  }

  object File {
    val A = 72340172838076673l
    val B = 144680345676153346l
    val C = 289360691352306692l
    val D = 578721382704613384l
    val E = 1157442765409226768l
    val F = 2314885530818453536l
    val G = 4629771061636907072l
    val H = -9187201950435737472l
  }

  object Rank {
    val _1 = 255l
    val _2 = 65280l
    val _3 = 16711680l
    val _4 = 4278190080l
    val _5 = 1095216660480l
    val _6 = 280375465082880l
    val _7 = 71776119061217280l
    val _8 = -72057594037927936l
  }

  object Square {
    val A1 = 1l
    val B1 = 2l
    val C1 = 4l
    val D1 = 8l
    val E1 = 16l
    val F1 = 32l
    val G1 = 64l
    val H1 = 128l
    val A2 = 256l
    val B2 = 512l
    val C2 = 1024l
    val D2 = 2048l
    val E2 = 4096l
    val F2 = 8192l
    val G2 = 16384l
    val H2 = 32768l
    val A3 = 65536l
    val B3 = 131072l
    val C3 = 262144l
    val D3 = 524288l
    val E3 = 1048576l
    val F3 = 2097152l
    val G3 = 4194304l
    val H3 = 8388608l
    val A4 = 16777216l
    val B4 = 33554432l
    val C4 = 67108864l
    val D4 = 134217728l
    val E4 = 268435456l
    val F4 = 536870912l
    val G4 = 1073741824l
    val H4 = 2147483648l
    val A5 = 4294967296l
    val B5 = 8589934592l
    val C5 = 17179869184l
    val D5 = 34359738368l
    val E5 = 68719476736l
    val F5 = 137438953472l
    val G5 = 274877906944l
    val H5 = 549755813888l
    val A6 = 1099511627776l
    val B6 = 2199023255552l
    val C6 = 4398046511104l
    val D6 = 8796093022208l
    val E6 = 17592186044416l
    val F6 = 35184372088832l
    val G6 = 70368744177664l
    val H6 = 140737488355328l
    val A7 = 281474976710656l
    val B7 = 562949953421312l
    val C7 = 1125899906842624l
    val D7 = 2251799813685248l
    val E7 = 4503599627370496l
    val F7 = 9007199254740992l
    val G7 = 18014398509481984l
    val H7 = 36028797018963968l
    val A8 = 72057594037927936l
    val B8 = 144115188075855872l
    val C8 = 288230376151711744l
    val D8 = 576460752303423488l
    val E8 = 1152921504606846976l
    val F8 = 2305843009213693952l
    val G8 = 4611686018427387904l
    val H8 = -9223372036854775808l
  }
}