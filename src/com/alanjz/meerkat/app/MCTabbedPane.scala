package com.alanjz.meerkat.app

import javax.swing.JTabbedPane

import com.alanjz.meerkat.app.tabs.MCWelcomeTab

class MCTabbedPane {
  private val tabbedPane = new JTabbedPane()

  this.addTab("Welcome", MCWelcomeTab)

  def toJTabbedPane = tabbedPane
}

object MCTabbedPane {
  implicit def toJTabbedPane(lhs : MCTabbedPane) : JTabbedPane = lhs.toJTabbedPane
}
