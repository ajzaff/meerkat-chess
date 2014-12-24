package com.alanjz.meerkat.app.menu

import java.awt.event.ActionEvent
import javax.swing._
import javax.swing.Action._

abstract class MCMenuItem(val name : String,
                          keyCode : Int,
                          modifiers : Int = 0) {

  val f : (ActionEvent => Unit)

  val act = new AbstractAction {
    override def actionPerformed(e : ActionEvent) = f(e)
  }

  val menuItem = new JMenuItem(name) {
    setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers))
    addActionListener(act)
  }

  def toJMenuItem = menuItem
}

object MCMenuItem {
  implicit def toJMenuItem(lhs : MCMenuItem) : JMenuItem = lhs.toJMenuItem
}