package com.alanjz.meerkat.app.tabs

import java.awt.BorderLayout
import javax.swing.JLabel

object MCWelcomeTab extends MCTab {
  val welcome = new JLabel("Welcome to Meerkat Chess!")

  this.setLayout(new BorderLayout)
  this.add(welcome, BorderLayout.NORTH)
}
