package com.alanjz.meerkat.pieces

/**
 * Standard piece color base class.
 */

sealed abstract class Color(val name : String) {

  /**
   * A reference to the other team.
   */
  val other : Color

  /**
   * Gets the other color.
   * @see other
   * @return the other color.
   */

  implicit def unary_! = other

  /**
   * Gets the name of this color as a string.
   * @return the color as a string.
   */

  override def toString = name
}

/**
 * Standard piece color values.
 */

object Color {

  /**
   * Implicit casts a color to an integer.
   * @param color a color.
   * @return `1` if White; `-1` if black.
   */

  implicit def Color2Int(color : Color) = color match {
    case White => 1
    case Black => -1
  }

  /**
   * The standard color `White`.
   */

  case object White extends Color("white") {
    val other = Black
  }

  /**
   * The standard color `Black`.
   */

  case object Black extends Color("black") {
    val other = White
  }
}
