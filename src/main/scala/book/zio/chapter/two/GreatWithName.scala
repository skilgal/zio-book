package book.zio.chapter.two

// Using the Console, write a little program that asks the user what their name is, and then prints it out to them with a greeting.
import zio._

object HelloHuman extends App {
  import zio.console._

  def run(args: List[String]) =
    program.exitCode

  def program =
    (for {
      _        <- putStrLn("What is your name? ")
      userName <- getStrLn
      _        <- putStrLn(s"Hello, $userName")
    } yield 0) orElse ZIO.succeed(1)

}
