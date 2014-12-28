package com.alanjz.meerkat.variation.mutable

import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.variation.Variation

class LinearVariation extends Variation {
  private var _length = 0
  private var _moves : List[Move] = Nil
  override def length: Int = _length

  def clear(): Unit = {
    _moves = Nil
    _length = 0
  }

  def apply(i : Int) = _moves(length-i-1)

  def add(move : Move): Unit = {
    _moves +:= move
    _length += 1
  }

  def update(i : Int, move : Move) = if(apply(i) != move) {
    _moves = _moves.drop(length-i)
    _length -= length-i
    add(move)
    true
  } else false

  override def toString = _moves.reverse.mkString(" ")
}
