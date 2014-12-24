package com.alanjz.meerkat.app.menu.edit

import java.awt.event.{KeyEvent, ActionEvent}

import com.alanjz.meerkat.app.MCApp
import com.alanjz.meerkat.app.menu.MCMenuItem

object MCRedo extends MCMenuItem("Redo", KeyEvent.VK_RIGHT) {
  override val f = (_ : ActionEvent) => MCApp.redo()
}