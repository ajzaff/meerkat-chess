package com.alanjz.meerkat

/**
 * The abstract base class for all chess board files.
 * @param value the file number [0..7].
 * @param name the file letter [a..h].
 */

sealed abstract class File(value : Int, val name : Char) {

  /**
   * Converts the file to an integer.
   * @return an integer.
   */

  def toInt = value

  /**
   * Converts this file to a string.
   * @return a string representing this file.
   */

  override def toString = name.toString
}

/**
 * All file values.
 */

object File {

  /**
   * Gets the file given by the integer.
   * @param n an integer [0..7].
   * @return a file.
   */

  def apply(n : Int) =
    n match {
      case 0 => A
      case 1 => B
      case 2 => C
      case 3 => D
      case 4 => E
      case 5 => F
      case 6 => G
      case 7 => H
    }

  case object A extends File(0,'a')
  case object B extends File(1,'b')
  case object C extends File(2,'c')
  case object D extends File(3,'d')
  case object E extends File(4,'e')
  case object F extends File(5,'f')
  case object G extends File(6,'g')
  case object H extends File(7,'h')
}