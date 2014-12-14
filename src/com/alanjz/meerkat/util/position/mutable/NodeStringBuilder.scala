package com.alanjz.meerkat.util.position.mutable

import com.alanjz.meerkat.{File, Square}

@deprecated
object NodeStringBuilder {
  def mkString(node : BasicNode) = {
    var builder = StringBuilder.newBuilder
    for (r <- 8 to 1 by -1) {
      builder ++= s"$r "
      for (f <- 1 to 8) {
        val p = node( Square(8*(r-1)+f-1) )
        builder ++=
          (if(p.isEmpty) "  "
          else s"${p.get.toChar} ")
      }
      builder += '\n'
    }
    builder ++= "  "
    for(i <- File.A.toInt to File.H.toInt) {
      val f = File(i).toString.toUpperCase
      builder ++= s"$f "
    }
    builder.mkString
  }
}
