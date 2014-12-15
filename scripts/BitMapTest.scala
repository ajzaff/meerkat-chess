import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

object BitMapTest extends App {
  val all = BitMask.all
  val rank1= BitMask.Rank._1
  println(
    BitMask.mkString(all & ~rank1))

  println(
    BitMask.mkString(
      BitMask.Square.E5 << 8)
  )


  val mask : BitMask = 0//0x94994523abbcal

  println(BitMask.mkString(mask))

  println(BitMask.mkString(mask & -mask))

  println(BitMask.mkString(mask & (mask-1)))

  println( BitMask.bitScanForward(mask) )

  println( BitMask.bitScanReverse(mask) )

  println(1l >> 8)

}
