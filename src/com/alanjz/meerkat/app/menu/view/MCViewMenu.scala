package com.alanjz.meerkat.app.menu.view

import com.alanjz.meerkat.app.menu.MCMenu

object MCViewMenu extends MCMenu("View") {
  this.add(MCFlipBoard)
  this.addSeparator()
  this.add(MCFullScreen)
}
