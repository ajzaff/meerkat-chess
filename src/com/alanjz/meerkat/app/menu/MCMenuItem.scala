package com.alanjz.meerkat.app.menu

import java.awt.event.ActionEvent
import javax.swing._
import javax.swing.Action._

abstract class MCMenuItem(val name : String,
                          keyCode : Int,
                          modifiers : Int = 0) {

  val menuItem = new JMenuItem(name) {
    setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers))
  }

  val act : (ActionEvent => Unit)

  val action = new AbstractAction {
    override def actionPerformed(e : ActionEvent) = act(e)
  }

  def toJMenuItem = menuItem
}

object MCMenuItem {
  implicit def toJMenuItem(lhs : MCMenuItem) : JMenuItem = lhs.toJMenuItem
}