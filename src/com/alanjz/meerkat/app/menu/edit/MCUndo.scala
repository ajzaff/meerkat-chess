package com.alanjz.meerkat.app.menu.edit

import java.awt.event.{KeyEvent, ActionEvent}

import com.alanjz.meerkat.app.MCApp
import com.alanjz.meerkat.app.menu.MCMenuItem

object MCUndo extends MCMenuItem("Undo", KeyEvent.VK_LEFT) {
  override val act = (_ : ActionEvent) => MCApp.undo()
}