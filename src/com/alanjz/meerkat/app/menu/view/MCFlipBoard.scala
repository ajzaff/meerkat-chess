package com.alanjz.meerkat.app.menu.view

import java.awt.event.{ActionEvent, KeyEvent}

import com.alanjz.meerkat.app.MCApp
import com.alanjz.meerkat.app.menu.MCMenuItem

object MCFlipBoard extends MCMenuItem("Flip Board", KeyEvent.VK_F, ActionEvent.CTRL_MASK) {
  override val f = (_ : ActionEvent) => MCApp.frame.board.flip()
}
