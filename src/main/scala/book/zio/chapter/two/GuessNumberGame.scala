package book.zio.chapter.two

import zio._

object NumberGuessing extends App {

  import zio.console._
  import zio.random._

  def analyzeAnswer(random: Int, guess: String) =
    if (random.toString == guess.trim) putStrLn("You guessed correctly")
    else putStrLn(s"Your guess is wrong. Correct number is ${random}")

  def run(args: List[String]) =
    guesser.exitCode

  val guesser = (for {
    number <- nextIntBounded(3)
    _      <- putStrLn("Input your guess number from 0 to 3")
    guess  <- getStrLn
    _      <- analyzeAnswer(number, guess)
  } yield 0) orElse ZIO.succeed(1)

}
