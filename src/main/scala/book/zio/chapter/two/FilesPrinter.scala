package book.zio.chapter.two

import zio._
// Using the following code as a foundation, write a ZIO application that prints out the contents of whatever
// files are passed into the program as command-line arguments. You should use the functions readFileZio
// and writeFileZio that you developed in these exercises, as well as ZIO.foreach.
object FilesPrinter extends App {
  import book.zio.chapter.two.FileUtils

  def run(commandLineArguments: List[String]) = app(commandLineArguments).exitCode

  def app(arguments: List[String]) =
    ZIO.foreach(arguments)(t => FileUtils.readFileZio(t))
}
