package book.zio

import zio._

object ZioApps {

  // Using the following code as a foundation, write a ZIO application that prints out the contents of whatever
  // files are passed into the program as command-line arguments. You should use the functions readFileZio
  // and writeFileZio that you developed in these exercises, as well as ZIO.foreach.

  object Cat extends zio.App {
    import zio.console._

    def run(commandLineArguments: List[String]) = logic.exitCode

    val logic = putStrLn("Some text") *> ZIO.succeed(1)
    // ZIO.foreach(commandLineArguments)(FileUtils.readFileZio)
  }

  // Using ZIO.effectTotal, convert the following procedural function into a ZIO function:
  def currentTime(): Long                          = System.currentTimeMillis()
  lazy val currentTimeZIO: ZIO[Any, Nothing, Long] = ZIO.effectTotal(System.currentTimeMillis())

  import java.io.IOException
  import zio.console._

  def readUntil(
    acceptInput: String => Boolean
  ): ZIO[Console, IOException, String] = ???

// Using recursion, write a function that will continue evaluating the specified effect,
// until the specified user-defined function evaluates to true on the

// Using the Console and Random services in ZIO,
// write a little program that asks the user to guess a randomly chosen number between 1 and 3,
// and prints out if they were correct or not.

// Using the Console service and recursion, write a function that will repeatedly read input from the console
// until the specified user-defined function evaluates to true on the input. output of the effect.
  def doWhile[R, E, A](body: ZIO[R, E, A])(condition: A => Boolean): ZIO[R, E, A] = ???

}
