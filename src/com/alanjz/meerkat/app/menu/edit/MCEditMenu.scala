package com.alanjz.meerkat.app.menu.edit

import javax.swing.JMenuItem

import com.alanjz.meerkat.app.menu.MCMenu

object MCEditMenu extends MCMenu("Edit") {
  this.add(new JMenuItem("Setup Board"))
  this.add(new JMenuItem("Copy FEN"))
  this.add(new JMenuItem("Copy PGN"))
  this.add(new JMenuItem("Paste FEN"))
  this.add(new JMenuItem("Paste PGN"))
  this.addSeparator()
  this.add(MCUndo)
  this.add(MCRedo)
}
