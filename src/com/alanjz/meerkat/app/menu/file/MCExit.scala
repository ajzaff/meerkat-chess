package com.alanjz.meerkat.app.menu.file

import java.awt.event.{ActionEvent, KeyEvent}

import com.alanjz.meerkat.app.menu.MCMenuItem

object MCExit extends MCMenuItem("Exit", KeyEvent.VK_Q, ActionEvent.CTRL_MASK) {
  override val f = (_ : ActionEvent) => System.exit(0)
}
