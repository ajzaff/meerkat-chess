package com.alanjz.meerkat.app.menu.view

import java.awt.event.{ActionEvent, KeyEvent}

import com.alanjz.meerkat.app.menu.MCMenuItem

object MCFullScreen extends MCMenuItem("Enter Full Screen", KeyEvent.VK_F, ActionEvent.CTRL_MASK | ActionEvent.META_MASK){
  val f = (_ : ActionEvent) => {}
}
