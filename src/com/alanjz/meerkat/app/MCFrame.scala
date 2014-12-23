package com.alanjz.meerkat.app

import javax.swing.JFrame

/**
 * Created by alan on 12/22/14.
 */
class MCFrame {
  private val frame = new JFrame("Meerkat chess")
  val board = new MCBoardPane
  val menuBar = new MCMenuBar

  // initialize the frame.
  frame.add(board)
  frame.setJMenuBar(menuBar)
  frame.setResizable(false)
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setAlwaysOnTop(true)
  frame.pack()
  frame.setLocationRelativeTo(null)

  def toJFrame = frame
}

object MCFrame {
  implicit def toJFrame(lhs : MCFrame) : JFrame = lhs.toJFrame
}
