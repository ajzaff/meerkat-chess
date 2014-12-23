import com.alanjz.meerkat.Square._
import com.alanjz.meerkat.pieces.Color.White
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.util.basicMove.QueenMover
import com.alanjz.meerkat.util.position.immutable.NodeBuilder

object QueenBug7Test extends App {
  val node = {
    val builder = NodeBuilder.newInstance
    builder.putPiece(E1, Bishop(White))
    builder.putPiece(D1, Queen(White))
    builder.putPiece(C1, Bishop(White))
    builder.putPiece(A1, Rook(White))
    builder.putPiece(C2, Pawn(White))
    builder.putPiece(D2, Pawn(White))
    builder.putPiece(E2, Pawn(White))
    builder.result()
  }

  println(new QueenMover(node).queenMoves(D1))
  println(node.verboseString)
}
