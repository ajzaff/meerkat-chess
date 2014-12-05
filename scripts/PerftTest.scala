import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.util.basicMove.MoveGenerator
import com.alanjz.meerkat.util.parsing.FENParser

object PerftTest extends App {
  def divide(node : BasicNode, depth : Int) : Unit = {
    var num = 0
    if(depth == 0) return
    val moves = new MoveGenerator(node).generateAll
    for (move <- moves) {
      val a = perft(node.move(move), depth-1)
      move match {
        case m : Move.PawnPromote => println(s"${m.origin}${m.target}${m.promoted.toChar.toUpper} $a")
        case m : Move.PawnPromoteCapture => println(s"${m.origin}${m.target}${m.promoted.toChar.toUpper} $a")
        case _ => println(s"${move.origin}${move.target} $a")
      }
      num += a
    }
    println(num)
  }

  def perft(node : BasicNode, depth : Int) : Int = {
    var num = 0
    if(depth == 0) return 1
    val moves = new MoveGenerator(node).generateAll
    for (move <- moves) {
      num += perft(node.move(move), depth-1)
    }
    num
  }


  /*val node = {
    val builder = NodeBuilder.newInstance
    builder.putPiece(A8, Rook(Black))
    builder.putPiece(B8, Knight(Black))
    builder.putPiece(C8, Bishop(Black))
    builder.putPiece(D8, Queen(Black))
    builder.putPiece(E8, King(Black))
    builder.putPiece(F8, Bishop(Black))
    builder.putPiece(G8, Knight(Black))
    builder.putPiece(H8, Rook(Black))
    builder.putPiece(A6, Pawn(Black))
    builder.putPiece(B7, Pawn(Black))
    builder.putPiece(C7, Pawn(Black))
    builder.putPiece(D7, Pawn(Black))
    builder.putPiece(E7, Pawn(Black))
    builder.putPiece(F7, Pawn(Black))
    builder.putPiece(G7, Pawn(Black))
    builder.putPiece(H7, Pawn(Black))

    builder.putPiece(A2, Pawn(White))
    builder.putPiece(B2, Pawn(White))
    builder.putPiece(C2, Pawn(White))
    builder.putPiece(D2, Pawn(White))
    builder.putPiece(E2, Pawn(White))
    builder.putPiece(F2, Pawn(White))
    builder.putPiece(G2, Pawn(White))
    builder.putPiece(H2, Pawn(White))
    builder.putPiece(A1, Rook(White))
    builder.putPiece(C3, Knight(White))
    builder.putPiece(C1, Bishop(White))
    builder.putPiece(D1, Queen(White))
    builder.putPiece(E1, King(White))
    builder.putPiece(F1, Bishop(White))
    builder.putPiece(G1, Knight(White))
    builder.putPiece(H1, Rook(White))
    builder.setActive(White)
    builder.result()
  }*/

  val node = FENParser.parse("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 2")


  // FEN: rnbqkbnr/pppppppp/8/8/8/2N5/PPPPPPPP/R1BQKBNR b KQkq - 1 1

  println(node.verboseString)

  val start = System.nanoTime()
  divide(node, 2)
  println(s"${(System.nanoTime() - start) / 1e9}s")

  //println(new MoveGenerator(node).generateAll.mkString(" "))

  //println(perft(node, 3))
}