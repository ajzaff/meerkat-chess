import com.alanjz.meerkat.Square._
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.util.basicMove.MoveGenerator
import com.alanjz.meerkat.util.position.immutable.NodeBuilder

object EnPassantTest extends App {
  val node = {
    val builder = NodeBuilder.newInstance
    builder.putPiece(G5, Pawn(Black))
    builder.putPiece(H5, Pawn(White))

    builder.setEnPassantSquare(G6)

    builder.result()
  }

  println(new MoveGenerator(node).generateAll.mkString(" "))
  println(node.verboseString)

  val node1 = {
    val builder = NodeBuilder.newInstance
    builder.putPiece(H3, Pawn(Black))
    builder.putPiece(G4, Pawn(White))

    builder.setEnPassantSquare(G2)
    builder.setActive(Black)

    builder.result()
  }

  println(new MoveGenerator(node1).generateAll.mkString(" "))
  println(node1.verboseString)
}
