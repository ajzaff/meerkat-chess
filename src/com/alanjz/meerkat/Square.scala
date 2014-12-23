package com.alanjz.meerkat

/**
 * The base class of all squares.
 */

sealed trait Square {

  /**
   * The file of this square.
   */

  val file : File

  /**
   * The rank of this square.
   */

  val rank : Rank

  /**
   * The value of this square [0..63].
   */

  def toInt : Int = 8*rank.toInt + file.toInt

  /**
   * Makes a string for this square (notation).
   * @return a string representing this square.
   */

  override def toString = s"$file$rank"
}

object Square {

  /**
   * Application gets a square from an integer.
   * @param n an integer [1..64].
   * @return a square.
   */

  def apply(n : Int) = n match {
    case 0 => A1
    case 1 => B1
    case 2 => C1
    case 3 => D1
    case 4 => E1
    case 5 => F1
    case 6 => G1
    case 7 => H1
    case 8 => A2
    case 9 => B2
    case 10 => C2
    case 11 => D2
    case 12 => E2
    case 13 => F2
    case 14 => G2
    case 15 => H2
    case 16 => A3
    case 17 => B3
    case 18 => C3
    case 19 => D3
    case 20 => E3
    case 21 => F3
    case 22 => G3
    case 23 => H3
    case 24 => A4
    case 25 => B4
    case 26 => C4
    case 27 => D4
    case 28 => E4
    case 29 => F4
    case 30 => G4
    case 31 => H4
    case 32 => A5
    case 33 => B5
    case 34 => C5
    case 35 => D5
    case 36 => E5
    case 37 => F5
    case 38 => G5
    case 39 => H5
    case 40 => A6
    case 41 => B6
    case 42 => C6
    case 43 => D6
    case 44 => E6
    case 45 => F6
    case 46 => G6
    case 47 => H6
    case 48 => A7
    case 49 => B7
    case 50 => C7
    case 51 => D7
    case 52 => E7
    case 53 => F7
    case 54 => G7
    case 55 => H7
    case 56 => A8
    case 57 => B8
    case 58 => C8
    case 59 => D8
    case 60 => E8
    case 61 => F8
    case 62 => G8
    case 63 => H8
  }

  implicit def Int2Square(i : Int) : Square = apply(i)

  case object A1 extends Square { val file = File.A; val rank = Rank._1 }
  case object B1 extends Square { val file = File.B; val rank = Rank._1 }
  case object C1 extends Square { val file = File.C; val rank = Rank._1 }
  case object D1 extends Square { val file = File.D; val rank = Rank._1 }
  case object E1 extends Square { val file = File.E; val rank = Rank._1 }
  case object F1 extends Square { val file = File.F; val rank = Rank._1 }
  case object G1 extends Square { val file = File.G; val rank = Rank._1 }
  case object H1 extends Square { val file = File.H; val rank = Rank._1 }
  case object A2 extends Square { val file = File.A; val rank = Rank._2 }
  case object B2 extends Square { val file = File.B; val rank = Rank._2 }
  case object C2 extends Square { val file = File.C; val rank = Rank._2 }
  case object D2 extends Square { val file = File.D; val rank = Rank._2 }
  case object E2 extends Square { val file = File.E; val rank = Rank._2 }
  case object F2 extends Square { val file = File.F; val rank = Rank._2 }
  case object G2 extends Square { val file = File.G; val rank = Rank._2 }
  case object H2 extends Square { val file = File.H; val rank = Rank._2 }
  case object A3 extends Square { val file = File.A; val rank = Rank._3 }
  case object B3 extends Square { val file = File.B; val rank = Rank._3 }
  case object C3 extends Square { val file = File.C; val rank = Rank._3 }
  case object D3 extends Square { val file = File.D; val rank = Rank._3 }
  case object E3 extends Square { val file = File.E; val rank = Rank._3 }
  case object F3 extends Square { val file = File.F; val rank = Rank._3 }
  case object G3 extends Square { val file = File.G; val rank = Rank._3 }
  case object H3 extends Square { val file = File.H; val rank = Rank._3 }
  case object A4 extends Square { val file = File.A; val rank = Rank._4 }
  case object B4 extends Square { val file = File.B; val rank = Rank._4 }
  case object C4 extends Square { val file = File.C; val rank = Rank._4 }
  case object D4 extends Square { val file = File.D; val rank = Rank._4 }
  case object E4 extends Square { val file = File.E; val rank = Rank._4 }
  case object F4 extends Square { val file = File.F; val rank = Rank._4 }
  case object G4 extends Square { val file = File.G; val rank = Rank._4 }
  case object H4 extends Square { val file = File.H; val rank = Rank._4 }
  case object A5 extends Square { val file = File.A; val rank = Rank._5 }
  case object B5 extends Square { val file = File.B; val rank = Rank._5 }
  case object C5 extends Square { val file = File.C; val rank = Rank._5 }
  case object D5 extends Square { val file = File.D; val rank = Rank._5 }
  case object E5 extends Square { val file = File.E; val rank = Rank._5 }
  case object F5 extends Square { val file = File.F; val rank = Rank._5 }
  case object G5 extends Square { val file = File.G; val rank = Rank._5 }
  case object H5 extends Square { val file = File.H; val rank = Rank._5 }
  case object A6 extends Square { val file = File.A; val rank = Rank._6 }
  case object B6 extends Square { val file = File.B; val rank = Rank._6 }
  case object C6 extends Square { val file = File.C; val rank = Rank._6 }
  case object D6 extends Square { val file = File.D; val rank = Rank._6 }
  case object E6 extends Square { val file = File.E; val rank = Rank._6 }
  case object F6 extends Square { val file = File.F; val rank = Rank._6 }
  case object G6 extends Square { val file = File.G; val rank = Rank._6 }
  case object H6 extends Square { val file = File.H; val rank = Rank._6 }
  case object A7 extends Square { val file = File.A; val rank = Rank._7 }
  case object B7 extends Square { val file = File.B; val rank = Rank._7 }
  case object C7 extends Square { val file = File.C; val rank = Rank._7 }
  case object D7 extends Square { val file = File.D; val rank = Rank._7 }
  case object E7 extends Square { val file = File.E; val rank = Rank._7 }
  case object F7 extends Square { val file = File.F; val rank = Rank._7 }
  case object G7 extends Square { val file = File.G; val rank = Rank._7 }
  case object H7 extends Square { val file = File.H; val rank = Rank._7 }
  case object A8 extends Square { val file = File.A; val rank = Rank._8 }
  case object B8 extends Square { val file = File.B; val rank = Rank._8 }
  case object C8 extends Square { val file = File.C; val rank = Rank._8 }
  case object D8 extends Square { val file = File.D; val rank = Rank._8 }
  case object E8 extends Square { val file = File.E; val rank = Rank._8 }
  case object F8 extends Square { val file = File.F; val rank = Rank._8 }
  case object G8 extends Square { val file = File.G; val rank = Rank._8 }
  case object H8 extends Square { val file = File.H; val rank = Rank._8 }
}
