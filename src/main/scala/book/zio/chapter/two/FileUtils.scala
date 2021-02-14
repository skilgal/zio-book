package book.zio.chapter.two

import zio._

object FileUtils {
  // Implementa ZIO version of the function readFile by using the ZIO.effect constructor
  def readFile(file: String): String = {
    val source = scala.io.Source.fromFile(file)
    try source.getLines().mkString
    finally source.close()
  }

  def readFileZio(file: String) = {
    import scala.io.Source
    for {
      source  <- ZIO.effect(Source.fromFile(file))
      content <- ZIO.effectTotal(source.getLines().mkString)
      _       <- ZIO.effect(source.close())
    } yield content
  }

  // Implement a ZIO version of the function writeFile by using the ZIO.effect constructor.
  def writeFile(file: String, text: String): Unit = {
    import java.io._
    val pw = new PrintWriter(new File(file))
    try pw.write(text)
    finally pw.close
  }

  def writeFileZio(file: String, text: String) = {
    import java.io._
    for {
      writer <- ZIO.effectTotal(new PrintWriter(new File(file)))
      _      <- ZIO.effectTotal(writer.write(text))
      _      <- ZIO.effect(writer.close())
    } yield ()
  }

  // Using the flatMap method of ZIO effects,
  // together with the readFileZio and writeFileZio functions that you wrote,
  // implement a ZIO version of the function copyFile.
  def copyFile(source: String, dest: String): Unit = {
    val contents = readFile(source)
    writeFile(dest, contents)
  }

  def copyFileZio(source: String, dest: String) =
    readFileZio(source).flatMap { content =>
      writeFileZio(dest, content)
    }
}
