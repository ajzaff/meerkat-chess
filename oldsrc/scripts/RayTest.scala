import com.alanjz.meerkat.Square
import com.alanjz.meerkat.util.iterator.square.RaySquareIterator
import RaySquareIterator._
import com.alanjz.meerkat.util.iterator.square.RaySquareIterator

object RayTest extends App {
  val iter = RaySquareIterator.newInstance(Square.H8, Southwest)

  println(iter.mkString(" "))
}
