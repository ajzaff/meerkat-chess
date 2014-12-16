package com.alanjz.meerkat.util.numerics

/**
 * Created by alan on 12/13/14.
 */
object CastleMask {
  type CastleMask = Int
  val empty : CastleMask = 0
  val shortWhite : CastleMask = 0x8
  val shortBlack : CastleMask = 0x2
  val longWhite : CastleMask = 0x4
  val longBlack : CastleMask = 0x1
  val all : CastleMask = 0xf

  def mkString(mask : CastleMask) : String ={
    if(mask == empty) return "-"
    val builder = StringBuilder.newBuilder
    if((mask & shortWhite) != empty) {
      builder += 'K'
    }
    if((mask & longWhite) != empty) {
      builder += 'Q'
    }
    if((mask & shortBlack) != empty) {
      builder += 'k'
    }
    if((mask & longBlack) != empty) {
      builder += 'q'
    }
    builder.mkString
  }
}
