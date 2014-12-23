package com.alanjz.meerkat.app

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import com.alanjz.meerkat.app.MCBoardPane._

import java.awt._
import javax.swing.JPanel
import java.io.File

import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.pieces.Piece
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

class MCBoardPane {

  private val panel = new JPanel {
    override def paint(g : Graphics) = _paint(g)
  }

  // initialize panel.
  panel.setPreferredSize(boardSize)


  private def _paint(g : Graphics) : Unit = {
    g.setColor(Color.gray)
    g.fillRect(0,0,panel.getWidth,panel.getHeight)
    paintBorder(g)
    paintBoard(g)
    paintPieces(g)
  }

  private def paintBorder(g : Graphics) : Unit = {
    val g2d = g.asInstanceOf[Graphics2D]
    g2d.setStroke(new BasicStroke(borderSize))
    g2d.setColor(borderColor)
    g2d.drawRect(borderSize/2,
      borderSize/2,
      panel.getWidth-borderSize,
      panel.getHeight-borderSize)

    g2d.setColor(borderTextColor)
    g2d.setFont(borderFont)
    for(i <- 0 to 7) {
      val file = ('a'+i).toChar
      val rank = ('1'+i).toChar

      // calculate the locations.
      val rX = borderSize/2 - g.getFontMetrics.charWidth(rank)/2
      val rY = borderSize + (7-i)*squareSize + squareSize/2 + g.getFontMetrics.getHeight / 2
      val fX = borderSize + i*squareSize + squareSize/2 - g.getFontMetrics.charWidth(file)/2
      val fY = borderSize + 8*squareSize + borderSize/2 + g.getFontMetrics.getDescent / 2

      // draw the strings.
      g2d.drawString(file.toString, fX, fY)
      g2d.drawString(rank.toString, rX, rY)
    }

    g2d.setStroke(new BasicStroke(1))
    g2d.setColor(borderTextColor)
    g2d.drawRect(borderSize-1,
      borderSize-1,
      panel.getWidth-2*borderSize+1,
      panel.getHeight-2*borderSize+1)
  }

  private def paintBoard(g : Graphics) : Unit = {
    for(i <- 0 to 7) {
      for(j <- 0 to 7) {
        g.setColor(if(i%2 == j%2) {
          darkSquareColor
        } else {
          lightSquareColor
        })
        g.fillRect(borderSize + i*squareSize, borderSize + j*squareSize, squareSize, squareSize)
      }
    }
  }

  private def paintPieces(g : Graphics) : Unit = {
    def piecePath(piece : Piece) : String = s"images/${piece.name}-${piece.color.toString}-sm.png"
    def drawBM(bm : BitMask, image : BufferedImage) : Unit = {
      var mask = bm
      while(mask != BitMask.empty) {
        val lsb = BitMask.bitScanForward(mask)
        val x = borderSize + (lsb%8)*squareSize + squareSize/2 - pieceSize/2
        val y = borderSize + (7-lsb/8)*squareSize + squareSize/2 - pieceSize/2
        g.drawImage(image,x,y,null)
        mask &= (mask-1)
      }
    }

    // draw all the pieces.
    val pos = MCApp.position
    drawBM (pos.whitePawns, ImageIO read new File(piecePath(Pawn(White))) )
    drawBM (pos.whiteKnights, ImageIO read new File(piecePath(Knight(White))) )
    drawBM (pos.whiteBishops, ImageIO read new File(piecePath(Bishop(White))) )
    drawBM (pos.whiteRooks, ImageIO read new File(piecePath(Rook(White))) )
    drawBM (pos.whiteQueens, ImageIO read new File(piecePath(Queen(White))) )
    drawBM (pos.whiteKing, ImageIO read new File(piecePath(King(White))) )
    drawBM (pos.blackPawns, ImageIO read new File(piecePath(Pawn(Black))) )
    drawBM (pos.blackKnights, ImageIO read new File(piecePath(Knight(Black))) )
    drawBM (pos.blackBishops, ImageIO read new File(piecePath(Bishop(Black))) )
    drawBM (pos.blackRooks, ImageIO read new File(piecePath(Rook(Black))) )
    drawBM (pos.blackQueens, ImageIO read new File(piecePath(Queen(Black))) )
    drawBM (pos.blackKing, ImageIO read new File(piecePath(King(Black))) )
  }

  def toJPanel = panel
}

object MCBoardPane {

  // defaults.
  val borderSize = 47
  val squareSize = 72
  val pieceSize = 64
  val boardSize = new Dimension(2*borderSize + 8*squareSize, 2*borderSize + 8*squareSize)
  val borderFont = new Font(Font.MONOSPACED, Font.BOLD, 20)
  val borderColor = Color.gray
  val borderTextColor = borderColor.brighter
  val darkSquareColor = new Color(156,203,239)
  val lightSquareColor = new Color(227,238,246)

  implicit def toJPanel(lhs : MCBoardPane) : JPanel = lhs.toJPanel
}