package book.zio

import zio._

object Common {

  // Using ZIO.effectTotal, convert the following procedural function into a ZIO function:
  def currentTime(): Long                          = System.currentTimeMillis()
  lazy val currentTimeZIO: ZIO[Any, Nothing, Long] = ZIO.effectTotal(System.currentTimeMillis())

}
