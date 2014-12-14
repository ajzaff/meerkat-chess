import java.math.BigInteger

object BigIntegerTest extends App {
  var long : Long = 1
  var start = System.nanoTime()
  for(i <- 0 to 10000000) {
    long <<= 9
  }
  println(System.nanoTime() - start)

  var bigInt = new BigInteger(1, Array.ofDim[Byte](16))
  start = System.nanoTime()
  for(i <- 0 to 10000000) {
    bigInt = bigInt.shiftLeft(9)
  }
  println(System.nanoTime() - start)
}
