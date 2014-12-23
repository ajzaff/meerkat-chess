package com.alanjz.meerkat.util.position.mutable

import com.alanjz.meerkat.Square
import com.alanjz.meerkat.pieces.Color.{Black, White}
import com.alanjz.meerkat.position.mutable.MaskNode
import com.alanjz.meerkat.util.iterator.bitmask.FENIterator
import com.alanjz.meerkat.util.numerics.{BitMask, CastleMask}

object FENMaskNodeBuilder {

   def parse(fen : String) : MaskNode = {
     val node = new MaskNode()
     val iter = new FENIterator()
     var i = 0
     while(fen(i) != ' ') {
       val c = fen(i)
       c match {
         case 'P' => node.whitePawns |= iter.next()
         case 'N' => node.whiteKnights |= iter.next()
         case 'B' => node.whiteBishops |= iter.next()
         case 'R' => node.whiteRooks |= iter.next()
         case 'Q' => node.whiteQueens |= iter.next()
         case 'K' => node.whiteKing |= iter.next()
         case 'p' => node.blackPawns |= iter.next()
         case 'n' => node.blackKnights |= iter.next()
         case 'b' => node.blackBishops |= iter.next()
         case 'r' => node.blackRooks |= iter.next()
         case 'q' => node.blackQueens |= iter.next()
         case 'k' => node.blackKing |= iter.next()
         case '/' =>
         case _ => if(c.isDigit) {
           for(j <- 1 to c.asDigit) {
             iter.next()
           }
         }
       }
       i += 1
     }
     i += 1
     if(fen(i) == 'b') node.active = Black
     else if(fen(i) == 'w') node.active = White
     else throw new Exception(s"Unexpected active player `${fen(i)}`")
     i += 2
     node.castleMask = CastleMask.empty
     if(fen(i) != '-') {
       while (fen(i) != ' ') {
         fen(i) match {
           case 'K' => node.castleMask |= CastleMask.shortWhite
           case 'Q' => node.castleMask |= CastleMask.longWhite
           case 'k' => node.castleMask |= CastleMask.shortBlack
           case 'q' => node.castleMask |= CastleMask.longBlack
         }
         i += 1
       }
     } else i += 1
     i += 1
     if(fen(i) == '-') {
       node.enPassant = BitMask.empty
       i += 2
     }
     else {
       val file : Int = fen(i)-'a'
       val rank : Int = fen(i+1)-'1'
       val ep = file + 8*rank
       node.enPassant = 1l << ep
       i += 3
     }
     val integers = fen.drop(i).split(" ")
     node.fullMove = integers(1).toInt
     node.halfMove = integers(0).toInt
     node.whitePieces = node.whitePawns |
       node.whiteKnights |
       node.whiteBishops |
       node.whiteRooks |
       node.whiteQueens |
       node.whiteKing
     node.blackPieces = node.blackPawns |
       node.blackKnights |
       node.blackBishops |
       node.blackRooks |
       node.blackQueens |
       node.blackKing
     node.allPieces = node.whitePieces | node.blackPieces
     node
   }
 }