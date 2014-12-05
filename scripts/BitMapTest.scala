import com.alanjz.meerkat.util.numerics.BitMask

object BitMapTest extends App {
  val all = BitMask.all
  val rank1= BitMask.Rank._1
  println(
    BitMask.mkString(all & ~rank1))

  println(
    BitMask.mkString(
      BitMask.Square.E5 << 8)
  )
}
