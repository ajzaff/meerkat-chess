import com.alanjz.meerkat.Square._
import com.alanjz.meerkat.pieces.Color._
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.util.NodeBuilder
import com.alanjz.meerkat.util.basicMove.MoveGenerator

object CastleTest extends App {
  var node = {
    val builder = NodeBuilder.newInstance
    builder.putPiece(E1, King(White))
    builder.putPiece(A1, Rook(White))
    builder.putPiece(H1, Rook(White))
    builder.putPiece(G2, King(Black))
    builder.putPiece(A8, Rook(Black))
    builder.putPiece(H8, Rook(Black))
    builder.putPiece(E3, Knight(Black))
    builder.result()
  }

  val moves1 = new MoveGenerator(node).generateAll
  println("white: " + moves1.mkString(" "))

  println(node)

 /* val moves2 = new KingMover(node as Black).kingCastling(E8)
  println("black: " + moves2.mkString(" "))

  println(node)*/
}
