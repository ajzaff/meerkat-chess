import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.position.BasicNode
import com.alanjz.meerkat.search.AlphaBetaSearch
import com.alanjz.meerkat.util.basicMove.MoveGenerator

object AlphaBetaEfficiencyTest extends App {
  var node = BasicNode.startingPosition

  val start = System.nanoTime()
  var moves : List[Move] = null
  do {
    moves = new MoveGenerator(node).generateAll
    if(!moves.isEmpty) {
      val move = new AlphaBetaSearch(2).search(node)
      node = node.move(move._1)
    }
  }
  while(!moves.isEmpty && node.halfMove < 50)
  println((System.nanoTime()-start)/1e9 + "s")
}
