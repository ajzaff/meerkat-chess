package com.alanjz.meerkat.app.menu

import javax.swing.JMenu

abstract class MCMenu(name : String) {
  private val menu = new JMenu(name)

  def toJMenu = menu
}

object MCMenu {
  implicit def toJMenu(lhs : MCMenu) : JMenu = lhs.toJMenu
}
