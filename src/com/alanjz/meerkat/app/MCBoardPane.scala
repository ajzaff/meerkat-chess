package com.alanjz.meerkat.app

import java.awt.event.{ActionEvent, KeyEvent}
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.app.MCBoardPane._

import java.awt._
import javax.swing.{AbstractAction, JComponent, KeyStroke, JPanel}
import java.io.File

import com.alanjz.meerkat.app.menu.edit.{MCRedo, MCUndo}
import com.alanjz.meerkat.moves.Move
import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.pieces.Piece
import com.alanjz.meerkat.pieces.Piece._
import com.alanjz.meerkat.util.numerics.BitMask
import com.alanjz.meerkat.util.numerics.BitMask.BitMask

class MCBoardPane {

  private var flipped = false

  private val panel = new JPanel {
    override def paint(g : Graphics) = _paint(g)
  }

  val boardImage : BufferedImage = new BufferedImage(
    2*borderSize + 8*squareSize, 2*borderSize + 8*squareSize, BufferedImage.TYPE_INT_ARGB
  )
  private var boardBuffered = false

  // initialize panel.
  this.setPreferredSize(boardSize)
  this.setFocusable(true)
  this.setFocusCycleRoot(true)

  // events.
  this.addMouseListener(MCApp.mouse)
  this.addMouseMotionListener(MCApp.mouse)

  def isFlipped = flipped
  def flip() = {
    flipped = !flipped
    boardBuffered = false

    MCApp.mouse.clearHolding()
    updatePieceImageCache()
  }

  private def setRenderingHints(g2d : Graphics2D) : Unit = {
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
  }

  private def _paint(g : Graphics) : Unit = {
    val g2d = g.asInstanceOf[Graphics2D]

    setRenderingHints(g2d)

    g2d.setColor(Color.gray)
    g2d.fillRect(0,0,panel.getWidth,panel.getHeight)
    paintBoard(g2d)
    paintPieces(g2d)
  }

  private def paintBorder(g2d : Graphics2D) : Unit = {
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
      val rX = borderSize/2 - g2d.getFontMetrics.charWidth(rank)/2
      val rY = borderSize + (if(isFlipped) i else 7-i)*squareSize + squareSize/2 + g2d.getFontMetrics.getHeight / 2
      val fX = borderSize + (if(isFlipped) 7-i else i)*squareSize + squareSize/2 - g2d.getFontMetrics.charWidth(file)/2
      val fY = borderSize + 8*squareSize + g2d.getFontMetrics.getHeight/2

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

  private def paintBoard(g2d : Graphics2D) : Unit = {

    if(boardBuffered) {
      g2d.drawImage(boardImage, 0, 0, null)
      return
    }

    val g = boardImage.getGraphics.asInstanceOf[Graphics2D]

    setRenderingHints(g)
    g.setBackground(new Color(0,0,0,0))
    g.clearRect(0,0,boardImage.getWidth,boardImage.getHeight)
    paintBorder(g)

    for(i <- 0 to 7) {
      for(j <- 0 to 7) {
        g.setColor(
          if ((if(isFlipped) 7-i else i) % 2 == j % 2) {
            darkSquareColor
          } else {
            lightSquareColor
          }
        )
        g.fillRect(borderSize + (if(isFlipped) 7-i else i) * squareSize, borderSize +
          (if(isFlipped) j else 7 - j) * squareSize, squareSize, squareSize)
      }
    }

    boardBuffered = true
    g2d.drawImage( boardImage, 0, 0, null)
  }

  val cachedPieceImage = new BufferedImage(
    2*borderSize + 8*squareSize, 2*borderSize + 8*squareSize, BufferedImage.TYPE_INT_ARGB)

  def updatePieceImageCache(): Unit = {
    val g2d = cachedPieceImage.getGraphics.asInstanceOf[Graphics2D]
    setRenderingHints(g2d)

    def drawBM(bm : BitMask, image : BufferedImage) : Unit = {
      var mask = bm
      while(mask != BitMask.empty) {
        val lsb = BitMask.bitScanForward(mask)
        if(Square(lsb) != MCApp.mouse.getHoldingPlace) {
          val x = borderSize + (if(isFlipped) 7-lsb%8 else lsb%8) * squareSize + squareSize / 2 - pieceSize / 2
          val y = borderSize + (if(isFlipped) lsb/8 else 7-lsb/8) * squareSize + squareSize / 2 - pieceSize / 2
          g2d.drawImage(image, x, y, pieceSize, pieceSize, null)
        }
        mask &= (mask - 1)
      }
    }

    // clear image rect.
    g2d.setBackground(new Color(0,0,0,0))
    g2d.clearRect(0, 0, cachedPieceImage.getWidth, cachedPieceImage.getHeight)

    // draw all the pieces.
    val pos = MCApp.position
    drawBM (pos.whitePawns, pieceImage(Pawn(White)))
    drawBM (pos.whiteKnights, pieceImage(Knight(White)))
    drawBM (pos.whiteBishops, pieceImage(Bishop(White)))
    drawBM (pos.whiteRooks, pieceImage(Rook(White)))
    drawBM (pos.whiteQueens, pieceImage(Queen(White)))
    drawBM (pos.whiteKing, pieceImage(King(White)))
    drawBM (pos.blackPawns, pieceImage(Pawn(Black)))
    drawBM (pos.blackKnights, pieceImage(Knight(Black)))
    drawBM (pos.blackBishops, pieceImage(Bishop(Black)))
    drawBM (pos.blackRooks, pieceImage(Rook(Black)))
    drawBM (pos.blackQueens, pieceImage(Queen(Black)))
    drawBM (pos.blackKing, pieceImage(King(Black)))
  }

  updatePieceImageCache()

  private def paintPieces(g2d : Graphics2D) : Unit = {

    // draw pieces.
    g2d.drawImage(cachedPieceImage,  0, 0, null)

    if(MCApp.mouse.isHolding) {
      val holdingPlace = MCApp.mouse.getHoldingPlace
      val x = MCApp.mouse.mouse.x - pieceSize/2
      val y = MCApp.mouse.mouse.y - pieceSize/2
      val piece = MCApp.position.at(MCApp.mouse.getHoldingPlace.toInt)

      if(piece.isEmpty) return

      // draw over the source board square.
      val i=holdingPlace.toInt%8
      val j=holdingPlace.toInt/8
      g2d.setColor(
        if ((if(isFlipped) 7-i else i) % 2 == j % 2) {
          darkSquareColor
        } else {
          lightSquareColor
        }
      )
      g2d.fillRect(borderSize + (if(isFlipped) 7-i else i) * squareSize,
        borderSize + (if(isFlipped) j else 7-j) * squareSize, squareSize, squareSize)

      // draw the piece.
      g2d.drawImage(ImageIO read piecePath(piece.get), x, y, pieceSize, pieceSize, null)

      // draw the piece's moves.
      paintMoves(g2d, MCApp.mouse.getHoldingPlace)
    }
  }

  private def paintMoves(g2d : Graphics2D, from : Square) : Unit = {
    val moves = MCApp.moves.filter(_.origin == from)

    g2d.setColor(Color.black)
    for(m <- moves) {
      m match {
        case c : Move with Move.Capture =>
          g2d.drawLine(
            borderSize + (if(isFlipped) 7-c.target.toInt%8 else c.target.toInt%8)*squareSize,
            borderSize + (if(isFlipped) c.target.toInt/8 else 7-c.target.toInt/8)*squareSize,
            borderSize + (if(isFlipped) 8-c.target.toInt%8 else c.target.toInt%8+1)*squareSize,
            borderSize + (if(isFlipped) c.target.toInt/8+1 else 8-c.target.toInt/8)*squareSize
          )
          g2d.drawLine(
            borderSize + (if(isFlipped) 7-c.target.toInt%8 else c.target.toInt%8)*squareSize,
            borderSize + (if(isFlipped) c.target.toInt/8+1 else 8-c.target.toInt/8)*squareSize,
            borderSize + (if(isFlipped) 8-c.target.toInt%8 else c.target.toInt%8+1)*squareSize,
            borderSize + (if(isFlipped) c.target.toInt/8 else 7-c.target.toInt/8)*squareSize
          )
        case _ =>
          g2d.drawOval(
            borderSize + (if(isFlipped) 7-m.target.toInt%8 else m.target.toInt%8)*squareSize,
            borderSize + (if(isFlipped) m.target.toInt/8 else 7-m.target.toInt/8)*squareSize, squareSize, squareSize)
      }
    }
  }

  def toJPanel = panel
}

object MCBoardPane {

  // defaults.
  val borderSize = 26
  val squareSize = 58
  val pieceSize = 50
  val boardSize = new Dimension(2*borderSize + 8*squareSize, 2*borderSize + 8*squareSize)
  val borderFont = new Font(Font.MONOSPACED, Font.BOLD, 20)
  val borderColor = Color.gray
  val borderTextColor = borderColor.brighter
  val selectedColor = Color.orange
  val darkSquareColor = new Color(156,203,239)
  val lightSquareColor = new Color(227,238,246)

  // piece images.
  def piecePath(piece : Piece) = new File(s"images/${piece.name}-${piece.color.toString}-sm.png")
  val pieceImage = Map(
    Pawn(White) -> ImageIO.read(piecePath(Pawn(White))),
    Knight(White) -> ImageIO.read(piecePath(Knight(White))),
    Bishop(White) -> ImageIO.read(piecePath(Bishop(White))),
    Rook(White) -> ImageIO.read(piecePath(Rook(White))),
    Queen(White) -> ImageIO.read(piecePath(Queen(White))),
    King(White) -> ImageIO.read(piecePath(King(White))),
    Pawn(Black) -> ImageIO.read(piecePath(Pawn(Black))),
    Knight(Black) -> ImageIO.read(piecePath(Knight(Black))),
    Bishop(Black) -> ImageIO.read(piecePath(Bishop(Black))),
    Rook(Black) -> ImageIO.read(piecePath(Rook(Black))),
    Queen(Black) -> ImageIO.read(piecePath(Queen(Black))),
    King(Black) -> ImageIO.read(piecePath(King(Black)))
  )

  implicit def toJPanel(lhs : MCBoardPane) : JPanel = lhs.toJPanel
}