package com.alanjz.meerkat.util.iterator

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.Square.{A1, H8}
import com.alanjz.meerkat.util.iterator.RaySquareIterator._

import scala.collection.mutable

/**
 * Iterates over files.
 * @param origin the origin square.
 * @param inclusiveAtOrigin whether to include the origin (default=`false`).
 */

class RaySquareIterator(val origin : Square,
                         order : Order,
                         inclusiveAtOrigin : Boolean = false) extends SquareIterator {

  /**
   * The iterator backed by this iterator.
   */

  private val iterator = {

    // a new move list builder.
    val builder = List.newBuilder[Square]

    // add the origin if inclusive.
    if(isInclusiveAtOrigin) {
      builder += origin
    }

    // the origin file.
    val originFile = origin.file

    // match based on the order parameter.
    order match {
      case North =>
        for(i <- origin.toInt+8 to H8.toInt by 8) {
          builder += Square(i)
        }
      case Northeast =>
        for(i <- origin.toInt+9 to H8.toInt by 9
            if originFile.toInt < Square(i).file.toInt) {
          builder += Square(i)
        }
      case East =>
        for(i <- origin.toInt+1 to Math.min(origin.toInt+8, 63) by 1
            if originFile.toInt < Square(i).file.toInt) {
          builder += Square(i)
        }
      case Southeast =>
        for(i <- origin.toInt-7 to A1.toInt by -7
            if originFile.toInt < Square(i).file.toInt) {
          builder += Square(i)
        }
      case South =>
        for(i <- origin.toInt-8 to A1.toInt by -8) {
          builder += Square(i)
        }
      case Southwest =>
        for(i <- origin.toInt-9 to A1.toInt by -9
            if originFile.toInt > Square(i).file.toInt) {
          builder += Square(i)
        }
      case West =>
        for(i <- origin.toInt-1 to Math.max(origin.toInt-8, 0) by -1
            if originFile.toInt > Square(i).file.toInt) {
          builder += Square(i)
        }
      case Northwest =>
        for(i <- origin.toInt+7 to H8.toInt by 7
            if originFile.toInt > Square(i).file.toInt) {
          builder += Square(i)
        }
    }

    // return the resulting list iterator.
    builder.result().iterator
  }

  /**
   * Whether this iterator contains the origin point.
   * @return `true` if the origin is included; `false` otherwise.
   */

  def isInclusiveAtOrigin = inclusiveAtOrigin

  /**
   * Whether the iterator has a next square.
   * @return `true` if it has a next element; `false` otherwise.
   */

  override def hasNext = iterator.hasNext

  /**
   * Gets the next square in the iterator.
   * @return the next square.
   */

  override def next() : Square =  iterator.next()

}

/**
 * Standard constructors.
 */

object RaySquareIterator {

  /**
   * Gets a new ordered file square iterator from a given square.
   * @param origin a square.
   * @param order `Up` or `Down`.
   * @return a new file square iterator.
   */

  def newInstance(origin : Square, order : Order) =
    new RaySquareIterator(origin, order)

  /**
   * The base class of `FileSquareIterator` orders.
   */

  abstract class Order(n : Int) {

    /**
     * Converts the order to an integer.
     * @return an integer.
     */

    def toInt = n
  }

  /**
   * The north direction (12:00; towards rank 8).
   */

  case object North extends Order(0)

  /**
   * The south direction (6:00; towards rank 1).
   */

  case object South extends Order(4)

  /**
   * The north-east direction (1:30; towards H8).
   */

  case object Northeast extends Order(1)

  /**
   * The east direction (3:00; toward file H).
   */

  case object East extends Order(2)

  /**
   * The south-east direction (4:30; towards H1).
   */

  case object Southeast extends Order(3)

  /**
   * The south-west direction (7:30; towards A1).
   */

  case object Southwest extends Order(5)

  /**
   * The west direction (9:00; towards file A).
   */

  case object West extends Order(6)

  /**
   * The north-west direction (10:30; towards A8).
   */

  case object Northwest extends Order(7)
}