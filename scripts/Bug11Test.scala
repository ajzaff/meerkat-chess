import com.alanjz.meerkat.Square._
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.util.NodeBuilder
import com.alanjz.meerkat.util.basicMove.MoveGenerator

object Bug11Test extends App {
  val node = {
    val builder = NodeBuilder.newInstance
    builder.putPiece(H3, King(White))
    builder.putPiece(G3, Pawn(Black))
    builder.putPiece(F4, Pawn(Black))
    builder.putPiece(G5, Pawn(Black))
    builder.putPiece(F8, Rook(Black))
    builder.putPiece(B8, King(Black))
    builder.putPiece(C4, Pawn(Black))
    builder.putPiece(B2, Pawn(Black))
    builder.result()
  }

  println(new MoveGenerator(node).generateAll.mkString(" "))
  println(new MoveGenerator(node as Black).generateAll.mkString(" "))
  println(node.verboseString)
}
