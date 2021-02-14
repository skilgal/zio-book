package book.zio.chapter.two

import zio._
import zio.console._
import java.io.IOException

object Loops {

  // Using the Console service and recursion, write a function that will repeat-edly
  // read input from the console until the specified user-defined function evaluates
  // to true on the input.
  def readUntil(acceptInput: String => Boolean): ZIO[Console, IOException, String] =
    getStrLn.filterOrElse(acceptInput) { _ =>
      readUntil(acceptInput)
    }

  // Using recursion, write a function that will continue evaluating the specified effect,
  // until the specified user-defined function evaluates to true on the output of the effect
  def doWhile[R, E, A](body: ZIO[R, E, A])(condition: A => Boolean): ZIO[R, E, A] =
    body.filterOrElse(condition) { _ =>
      doWhile(body)(condition)
    }
}
