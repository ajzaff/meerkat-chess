package com.alanjz.meerkat.app

import java.awt.FlowLayout
import javax.swing.JFrame

/**
 * Created by alan on 12/22/14.
 */
class MCFrame extends Runnable {
  private val frame = new JFrame("Meerkat chess")
  val board = new MCBoardPane
  val menuBar = new MCMenuBar
  val tabs = new MCTabbedPane
  private val th = new Thread(this)
  private var _running = false

  // initialize the frame.
  this.setLayout(new FlowLayout)
  this.add(board)
  this.add(tabs)
  this.setJMenuBar(menuBar)
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  this.pack()
  this.setLocationRelativeTo(null)

  def isRunning = _running

  def start() : Unit = {
    _running = true
    this.setVisible(true)
    th.start()
  }

  def stop() : Unit = _running = false

  override def run() : Unit = {
    while(isRunning) {
      this.repaint()
      Thread.sleep(10)
    }

    System.exit(0)
  }

  def toJFrame = frame
}

object MCFrame {
  implicit def toJFrame(lhs : MCFrame) : JFrame = lhs.toJFrame
}
