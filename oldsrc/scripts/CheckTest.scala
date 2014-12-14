import com.alanjz.meerkat.Square._
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.util.basicMove.MoveGenerator
import com.alanjz.meerkat.util.position.immutable.NodeBuilder

object CheckTest extends App {
  val node = {
    val builder = NodeBuilder.newInstance
    builder.putPiece(E4, Queen(Black))
    builder.putPiece(B1, King(White))
    builder.putPiece(H2, Queen(White))
    builder.result()
  }

  println(new MoveGenerator(node).generateAll.mkString(" "))
  println(node.verboseString)
}
