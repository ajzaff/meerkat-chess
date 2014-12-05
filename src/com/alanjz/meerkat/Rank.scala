package com.alanjz.meerkat

import com.alanjz.meerkat.pieces.Color
import com.alanjz.meerkat.pieces.Color._

/**
 * The abstract base class for all ranks.
 * @param value an integer [0..7].
 * @param name the name of a rank.
 */

sealed abstract class Rank(value : Int, val name : Char) {

  /**
   * Converts the rank to an integer.
   * @return an integer.
   */

  def toInt = value

  /**
   * Converts this rank to a string.
   * @return a string representing this rank.
   */

  override def toString = name.toString
}

/**
 * Rank values.
 */

object Rank {

  /**
   * Gets the back rank for the given color.
   * @param color a color.
   * @return a rank.
   */

  def back(color : Color) =
    color match {
      case White => _1
      case Black => _8
    }

  /**
   * Gets the promote rank for the given color.
   * @param color a color.
   * @return a rank.
   */

  def promote(color : Color) =
    color match {
      case White => _8
      case Black => _1
    }

  /**
   * Gets the home rank for each color.
   * @param color a color.
   * @return
   */

  def home(color : Color) =
    color match {
      case White => _2
      case Black => _7
    }

  /**
   * Gets the rank from an integer.
   * @param n an integer [0..7].
   * @return a rank.
   */

  def apply(n : Int) =
    n match {
      case 0 => _1
      case 1 => _2
      case 2 => _3
      case 3 => _4
      case 4 => _5
      case 5 => _6
      case 6 => _7
      case 7 => _8
    }

  case object _1 extends Rank(0,'1')
  case object _2 extends Rank(1,'2')
  case object _3 extends Rank(2,'3')
  case object _4 extends Rank(3,'4')
  case object _5 extends Rank(4,'5')
  case object _6 extends Rank(5,'6')
  case object _7 extends Rank(6,'7')
  case object _8 extends Rank(7,'8')
}