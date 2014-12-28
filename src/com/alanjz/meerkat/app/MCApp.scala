package com.alanjz.meerkat.app

import com.alanjz.meerkat.app.events.MCMouse
import com.alanjz.meerkat.moves.Move.Capture
import com.alanjz.meerkat.moves.{PseudoLegalMover, Move}
import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.variation.mutable.LinearVariation

object MCApp extends App {
  val mouse = new MCMouse
  val position = MaskNode.initialPosition
  var moves : List[Move] = getLegalMoves
  val frame = new MCFrame
  val splash = new MCSplash(frame)

  private val variation = new LinearVariation
  private var i = 0

  private def updateBoard(): Unit = {
    moves = getLegalMoves
    frame.board.updatePieceImageCache()
  }

  private def playSoundFor(move : Move) : Unit = move match {
    case _ : Capture => MCSound.playSound("sounds/capture.wav")
    case _ => MCSound.playSound("sounds/move.wav")
  }

  def make(move : Move) : Unit = {
    if(i==variation.length) {
      variation.add(move)
    }
    else {
      variation.update(i, move)
    }
    i += 1
    position.make(move)
    updateBoard()
    playSoundFor(move)
  }

  def undo() {
    if(i > 0) {
      i -= 1
      position.unmake()
      updateBoard()
      playSoundFor(variation(i))
    }
  }

  def redo() =
    if(variation.nonEmpty && i<variation.length) make(variation(i))

  def getLegalMoves = new PseudoLegalMover(position).getMoves.filter(m => {
    position.make(m)
    val legal = !position.isTerminal
    position.unmake()
    legal
  })

  splash.start()
}