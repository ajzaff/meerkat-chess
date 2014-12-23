import com.alanjz.meerkat.moves.{Attacker, QueenMover, PseudoLegalMover}
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.position.mutable.FENMaskNodeBuilder

/**
 * Created by alan on 12/15/14.
 */
object PositionTest extends App {
  val pos = FENMaskNodeBuilder.parse("r4r1k/p1pNqpb1/bn2pnp1/3P4/1p2P3/2N2Q1p/PPPBBPPP/R4K1R w - - 0 1")
  //val moves = new PseudoLegalMover(pos).getMoves

  pos.active = !pos.active
  println(BitMask.mkString(new Attacker(pos).getAttackers(pos.blackKing)))

  println("pawns: \n" +BitMask.mkString(18014398509481984l))
  println("bishops: \n" +BitMask.mkString(18014398509481984l))
  println("rooks: \n" +BitMask.mkString(6953699114060087296l))
  println("knights: \n" +BitMask.mkString(9077567998918656l))
  println("kings: \n" +BitMask.mkString(4665729213955833856l))

  //val move = moves.find(_.toString == "b5").get
  println(pos)
  //pos.make(move)*/
  //println(pos)
  //println(moves.mkString(" "))
}
