package com.alanjz.meerkat.util.iterator.square

import com.alanjz.meerkat.{Rank, Square}

/**
 * An iterator producing squares in FEN-parsing order;
 * That is the rank ordered sequence from ranks [8..1].
 */

class FENSquareIterator extends SquareIterator {

  /**
   * The backed iterator.
   */

  private lazy val iterator : Iterator[Square] = {
    val squares = Seq.newBuilder[Square]
    for(j <- Rank._8.toInt to Rank._1.toInt by -1)
      for(i <- 8*j to 8*j+7)
        squares += Square(i)
    squares.result().iterator
  }

  /**
   * Whether the iterator has a next element.
   * @return `true` if a next element exists; `false` otherwise.
   */

  override def hasNext = iterator.hasNext

  /**
   * Gets the next square in the sequence.
   * @return a square or an exception.
   */

  override def next : Square = iterator.next()
}

object FENSquareIterator {

  /**
   * Gets a new FEN-style iterator.
   * @return a new FEN-style square iterator.
   */

  def newInstance = new FENSquareIterator
}