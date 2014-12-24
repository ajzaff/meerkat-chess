package com.alanjz.meerkat.variation

trait Variation {
  def length : Int
  def nonEmpty = !isEmpty
  def isEmpty = length == 0
}