import com.alanjz.meerkat.Square
import com.alanjz.meerkat.util.iterator.RaySquareIterator
import com.alanjz.meerkat.util.iterator.RaySquareIterator._

object RayTest extends App {
  val iter = RaySquareIterator.newInstance(Square.H8, Southwest)

  println(iter.mkString(" "))
}
