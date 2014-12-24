package com.alanjz.meerkat.app

import com.alanjz.meerkat.app.events.MCMouse
import com.alanjz.meerkat.moves.{PseudoLegalMover, Move}
import com.alanjz.meerkat.position.mutable.MaskNode

object MCApp extends App {
  val mouse = new MCMouse
  val position = MaskNode.initialPosition
  var moves : List[Move] = getLegalMoves
  val frame = new MCFrame
  val splash = new MCSplash(frame)

  private var variation : List[Move] = Nil
  private var variationIndex = 0

  private def updateBoard(): Unit = {
    moves = getLegalMoves
    frame.board.updatePieceImageCache()
  }

  def make(move : Move) : Unit = {
    if(variationIndex == 0) {
      variation +:= move
    }
    else if(move != variation(variationIndex)) {
      println("abandoning variation")
      variation = variation.drop(variationIndex)
      variation +:= move
      variationIndex = 0
    }
    position.make(move)
    updateBoard()
  }

  def undo() {
    if(variationIndex < variation.size) {
      variationIndex += 1
      position.unmake()
      updateBoard()
    }
  }

  def redo(): Unit = {
    if(variationIndex > 0) {
      variationIndex -= 1
      make(variation(variationIndex))
    }
  }

  def getLegalMoves = new PseudoLegalMover(position).getMoves.filter(m => {
    position.make(m)
    val legal = !position.isTerminal
    position.unmake()
    legal
  })

  splash.start()
}