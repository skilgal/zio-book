package book.zio.chapter.three

import zio._
import test._
import zio.test.Assertion._

object ExampleSpec extends DefaultRunnableSpec {

  def spec =
    suite("ExampleSpec")(
      test("addition works") {
        assert(1 + 1)(equalTo(2))
      },
      testM("ZIO.succeed succeeds with specified value") {
        assertM(ZIO.succeed(1 + 1))(equalTo(2))
      },
      test("hasSameElement") {
        assert(List(1, 1, 2, 3))(hasSameElements(List(3, 2, 1, 1)))
      },
      testM("fails") {
        for {
          exit <- ZIO.effect(1 / 0).catchAll(_ => ZIO.fail(())).run
        } yield assert(exit)(fails(isUnit))
      }
    )

  import console._
  import zio.test.environment._

  val greet: ZIO[Console, Nothing, Unit] = for {
    name <- getStrLn.orDie
    _    <- putStrLn(s"Hello, $name!")
  } yield ()

  def specConsole = suite("ExampleSpec")(
    testM("greet says hello to the user") {

      for {
        _     <- TestConsole.feedLines("Jane")
        _     <- greet
        value <- TestConsole.output
      } yield assert(value)(equalTo(Vector("Hello, Jane!\n")))
    }
  )

  import zio.clock._
  import zio.duration._

  val goShopping: ZIO[Console with Clock, Nothing, Unit] =
    putStrLn("Going shopping!").delay(1.hour)

  def specClock =
    suite("ExampleSpec")(testM("goShopping delays for one hour") {
      for {
        fiber <- goShopping.fork
        _     <- TestClock.adjust(1.hour)
        _     <- fiber.join
      } yield assertCompletes
    })

  import zio.test.TestAspect._

  def specAspect = suite("ExampleSpec")(
    testM("this test will be repeated to ensure it is stable") {
      assertM(ZIO.succeed(1 + 1))(equalTo(2))
    } @@ nonFlaky
  )

  import zio.random._
  val intGen: Gen[Random, Int] = Gen.anyInt

  def specPropertyBased =
    suite("ExampleSpec")(testM("integer addition is associative") {
      check(intGen, intGen, intGen) { (x, y, z) =>
        val left  = (x + y) + z
        val right = x + (y + z)
        assert(left)(equalTo(right))
      }
    })
}
