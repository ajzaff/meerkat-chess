package com.alanjz.meerkat.app

import java.io.File
import java.net.URL
import javax.sound.sampled._

object MCSound {
  def playSound(path : String) : Unit = {
    val url = new URL(s"file://${new File("").getAbsolutePath}/$path")
    val audioIn = AudioSystem.getAudioInputStream(url)
    val clip = AudioSystem.getClip
    clip.open(audioIn)
    clip.start()
  }
}
