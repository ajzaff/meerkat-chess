package com.alanjz.meerkat.app.menu.help

import java.awt.event.{ActionEvent, KeyEvent}

import com.alanjz.meerkat.app.menu.MCMenuItem

object MCAbout extends MCMenuItem("About", KeyEvent.VK_F1) {
  val f = (_ : ActionEvent) => {}
}
