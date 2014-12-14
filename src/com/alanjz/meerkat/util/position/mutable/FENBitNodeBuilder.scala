package com.alanjz.meerkat.util.position.mutable

import com.alanjz.meerkat.position.mutable.BitNode

object FENBitNodeBuilder {

   def parse(fen : String) : BitNode = {
     val builder = new BitNode().newInstance
     var i = 0
     var rank = 0
     while(fen(i) != ' ') {
       val c = fen(i)
       c match {
         case 'P' => builder += Pawn(White)
         case 'N' => builder += Knight(White)
         case 'B' => builder += Bishop(White)
         case 'R' => builder += Rook(White)
         case 'Q' => builder += Queen(White)
         case 'K' => builder += King(White)
         case 'p' => builder += Pawn(Black)
         case 'n' => builder += Knight(Black)
         case 'b' => builder += Bishop(Black)
         case 'r' => builder += Rook(Black)
         case 'q' => builder += Queen(Black)
         case 'k' => builder += King(Black)
         case '/' => {}
         case _ => if(c.isDigit) {
           for(j <- 1 to c.asDigit) {
             builder += None
           }
         }
       }
       i += 1
     }
     i += 1
     if(fen(i) == 'b') builder.setActive(Black)
     else if(fen(i) == 'w') builder.setActive(White)
     else throw new Exception(s"Unexpected active player `${fen(i)}`")
     i += 2
     builder.setCastling(CastleRule.empty)
     if(fen(i) != '-') {
       while (fen(i) != ' ') {
         fen(i) match {
           case 'K' => builder.setCastling(builder.result().castleRules + KingCastleShort(White))
           case 'Q' => builder.setCastling(builder.result().castleRules + KingCastleLong(White))
           case 'k' => builder.setCastling(builder.result().castleRules + KingCastleShort(Black))
           case 'q' => builder.setCastling(builder.result().castleRules + KingCastleLong(Black))
         }
         i += 1
       }
     } else i += 1
     i += 1
     if(fen(i) == '-') {
       builder.setEnPassantSquare(None)
       i += 2
     }
     else {
       val file : Int = fen(i)-'a'
       val rank : Int = fen(i+1)-'1'
       val ep = Square(file + 8*rank)
       builder.setEnPassantSquare(Some(ep))
       i += 3
     }
     val integers = fen.drop(i).split(" ")
     builder.setFullMove(integers(1).toInt)
     builder.setHalfMove(integers(0).toInt)
     builder.result()
   }
 }