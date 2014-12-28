package com.alanjz.meerkat.app.events

import java.awt.Point
import java.awt.event.{MouseListener, MouseEvent, MouseMotionListener}

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.app.{MCSound, MCApp, MCBoardPane}
import com.alanjz.meerkat.moves.Move.Capture

class MCMouse extends MouseListener with MouseMotionListener {
  val mouse = new Point()
  private var _down = false
  private var _holding = false
  private var _holdingPlace : Square = null

  def isDown = _down
  def isHolding = _holding
  def getHoldingPlace = _holdingPlace

  def clearHolding() = {
    _holding = false
    _holdingPlace = null
  }

  override def mouseMoved(e: MouseEvent): Unit = {
    mouse.setLocation(e.getPoint)
  }
  override def mouseDragged(e: MouseEvent): Unit = {
    _down = true
    mouse.setLocation(e.getPoint)
  }
  override def mouseClicked(e: MouseEvent): Unit = {

  }
  override def mouseExited(e: MouseEvent): Unit = {

  }
  override def mouseEntered(e: MouseEvent): Unit = {

  }

  def hasHoverSquare : Boolean =
    mouse.x >= MCBoardPane.borderSize && mouse.x < MCBoardPane.borderSize + 8*MCBoardPane.squareSize &&
      mouse.y >= MCBoardPane.borderSize && mouse.y < MCBoardPane.borderSize + 8*MCBoardPane.squareSize

  def getHoverSquare : Option[Square] = if(hasHoverSquare) {
    val x = mouse.x - MCBoardPane.borderSize
    val y = mouse.y - MCBoardPane.borderSize
    val xc = if(MCApp.frame.board.isFlipped) {
      7-x/MCBoardPane.squareSize
    }
    else {
      x/MCBoardPane.squareSize
    }
    val yc = if(MCApp.frame.board.isFlipped) {
      y/MCBoardPane.squareSize
    }
    else {
      7-y/MCBoardPane.squareSize
    }

    Some(Square(xc + 8*yc))
  }
  else None

  override def mousePressed(e: MouseEvent): Unit = {
    _down = true

    // determine if the mouse coordinates fall inside a valid square.
    val square = getHoverSquare
    if(square.isDefined) {

      // If the square is occupied in the given position
      // Set te holding place, otherwise set the selected place.
      val piece = MCApp.position.at(square.get.toInt)
      if(piece.isDefined && piece.get.color == MCApp.position.active) {
        _holding = true
        _holdingPlace = square.get
      }
    }
  }
  override def mouseReleased(e: MouseEvent): Unit = {


    if(isHolding) {

      // determine if there is a valid landing square.
      val landing = getHoverSquare
      if(landing.isDefined) {

        // determine if the landing square is a valid selection.
        val targets = MCApp.moves.filter(_.origin == getHoldingPlace)
        val move = targets.find(_.target == landing.get)
        if(move.isDefined) {

          // make a move!
          MCApp.make(move.get)
        }
      }
    }

    _down = false
    clearHolding()
  }
}