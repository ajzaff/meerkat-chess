package com.alanjz.meerkat.app.tabs

import java.awt.Dimension
import javax.swing.JPanel

abstract class MCTab {
  private val panel = new JPanel()

  this.setPreferredSize(new Dimension(300, 650))

  def toJPanel = panel
}

object MCTab {
  implicit def toJPanel(lhs : MCTab) : JPanel = lhs.toJPanel
}