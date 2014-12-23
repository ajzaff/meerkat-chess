package com.alanjz.meerkat.app

import java.awt.{Graphics, Dimension}
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame

class MCSplash(mcFrame : MCFrame, secondsTimeout : Int = 5) extends Runnable {
  private val frame = new JFrame("Meerkat chess starting...") {
    override def paint(g : Graphics) = _paint(g)
  }
  private val splashImage = ImageIO.read(new File("images/splash.png"))
  private val th = new Thread(this)

  private def _paint(g : Graphics): Unit = {
    g.drawImage(splashImage, 0, 0, null)
  }

  def start() : Unit = {
    // Set up the frame.
    frame.setSize(new Dimension(splashImage.getWidth, splashImage.getHeight))
    frame.setLocationRelativeTo(null)
    frame.setUndecorated(true)
    frame.setAlwaysOnTop(true)
    frame.setVisible(true)

    // Start the timer.
    th.start()
  }

  override def run(): Unit = {
    val start = System.nanoTime()
    while((System.nanoTime() - start) / 1e9 < secondsTimeout) {
      Thread sleep 1000
    }

    frame.setVisible(false)
    mcFrame.setVisible(true)
  }

  def toJFrame = frame
}

object MCSplash {
  implicit def toJFrame(lhs : MCSplash) : JFrame = lhs.toJFrame
}