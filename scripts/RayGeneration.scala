import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

/**
 * Created by alan on 12/14/14.
 */
object RayGeneration extends App {

  def scary(i : Long, shift : Int) : Boolean = shift match {
    case 1 => (i+1) % 8 == 0
    case -1 => i % 8 == 0
    case 8 => false
    case -8 => false
    case 9 => (i+1) % 8 == 0
    case 7 => i % 8 == 0
    case -7 => (i+1) % 8 == 0
    case -9 => i % 8 == 0
    case _ => false
  }

  def ray(from : Long, shift : Int) : BitMask = {
    val end = if(shift < 0) 0 else 63
    var mask = BitMask.empty
    var i = from

    def n_end: Boolean =
      if(end <= 0) i >= end
      else i <= end

    while (n_end && !scary(i, shift))
    {
      i += shift
      if(n_end) {
        mask |= 1l << i
      }
    }
    mask
  }

  def square_str(i : Int) : String = {
    val rank = i/8
    val file = i%8
    val sb = StringBuilder.newBuilder
    sb += ('a'+file).toChar
    sb += ('1'+rank).toChar
    sb.mkString
  }

  def print_code(arrayName : String, delta : Int, debug : Boolean = false): Unit = {
    print(s"val $arrayName = Array[BitMask](")
    for(i <- 0 to 63) {
      val r = ray(i, delta)
      if(!debug && i % 4 == 0) print("\n\t")
      if(!debug) print(s"${r}l, ")
      if(debug) println( BitMask.mkString(r) )
    }
    println(")")
  }

  // 12:00 rays
  print_code("north", 8)
  // 1:30 rays
  print_code("northeast", 9)
  // 3:00 rays
  print_code("east", 1)
  // 4:30 rays
  print_code("southeast", -7)
  // 6:00 rays
  print_code("south", -8)
  // 7:30 rays
  print_code("southwest", -9)
  // 9:00 rays
  print_code("west", -1)
  // 10:30 rays
  print_code("northwest", 7)

}