package com.alanjz.meerkat.app

import javax.swing.JMenuBar

import com.alanjz.meerkat.app.menu.edit.MCEditMenu
import com.alanjz.meerkat.app.menu.engine.MCEngineMenu
import com.alanjz.meerkat.app.menu.file.MCFileMenu
import com.alanjz.meerkat.app.menu.game.MCGameMenu
import com.alanjz.meerkat.app.menu.settings.MCSettingsMenu

class MCMenuBar {
  private val menuBar = new JMenuBar

  // add menus.
  this.add(MCFileMenu)
  this.add(MCEditMenu)
  this.add(MCGameMenu)
  this.add(MCEngineMenu)
  this.add(MCSettingsMenu)

  def toJMenuBar = menuBar
}

object MCMenuBar {
  implicit def toJMenuBar(lhs : MCMenuBar) : JMenuBar = lhs.toJMenuBar
}
