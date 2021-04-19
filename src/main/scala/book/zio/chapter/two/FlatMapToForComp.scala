package book.zio.chapter.two

object FlatMapToForComp {
  import zio._

  def printLine(line: String) = ZIO.effect(println(line))
  val readLine                = ZIO.effect(scala.io.StdIn.readLine())

  // Rewrite the following ZIO code that uses flatMap into a for comprehension.
  printLine("What is your name?").flatMap(_ => readLine.flatMap(name => printLine(s"Hello, ${name}!")))

  // Result
  for {
    name <- printLine("What is your name?") *> readLine
    _    <- printLine(s"Hello, ${name}")
  } yield ()

  // Rewrite the following ZIO code that uses flatMap into a for comprehension.
  val random = ZIO.effect(scala.util.Random.nextInt(3) + 1)
  random.flatMap(int =>
    printLine("Guess a number from 1 to 3:").flatMap(_ =>
      readLine.flatMap(num =>
        if (num == int.toString) printLine("You guessed right!")
        else printLine(s"You guessed wrong, the number was ${int}!")
      )
    )
  )

  // Result
  for {
    int <- random <* printLine("Guess a number from 1 to 3:")
    num <- readLine
    _ <- {
      if (num == int.toString) printLine("You guessed right!")
      else printLine(s"You guessed wrong, the number was ${int}")
    }
  } yield ()

}
