package com.alanjz.meerkat.app

import javax.swing.JMenuBar

import com.alanjz.meerkat.app.menu.edit.MCEditMenu
import com.alanjz.meerkat.app.menu.file.MCFileMenu

class MCMenuBar {
  private val menuBar = new JMenuBar

  // add menus.
  this.add(MCFileMenu)
  this.add(MCEditMenu)

  def toJMenuBar = menuBar
}

object MCMenuBar {
  implicit def toJMenuBar(lhs : MCMenuBar) : JMenuBar = lhs.toJMenuBar
}
