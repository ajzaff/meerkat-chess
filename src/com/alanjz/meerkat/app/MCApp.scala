package com.alanjz.meerkat.app

import com.alanjz.meerkat.position.mutable.MaskNode

object MCApp extends App {
  val position = MaskNode.initialPosition
  val frame = new MCFrame
  val splash = new MCSplash(frame)
  splash.start()
}