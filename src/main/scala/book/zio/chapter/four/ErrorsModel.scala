package book.zio.chapter

package four {

  import zio._
  import console._

  import java.io.IOException
  import scala.Left

  object ErrorModel {
    // Using the appropriate effect constructor, fix the following function so that it no longer fails with defects
    // when executed. Make a note of how the inferred return type for the function changes
    def failWithMessage(string: String) = ZIO.fail(throw new Error(string))

    // Using the ZIO#foldCauseM operator and the Cause#defects method, implement the following function.
    // This function should take the effect, inspect defects, and if a suitable defect is found, it should recover
    // from the error with the help of the specified function, which generates a new success value for such a defect.
    def recoverFromSomeDefects[R, E, A](zio: ZIO[R, E, A])(f: Throwable => Option[A]): ZIO[R, E, A] =
      zio.foldCauseM(
        cause =>
          cause.defects
            .flatMap(f)
            .headOption
            .map(ZIO.succeed(_))
            .getOrElse(zio),
        _ => zio
      )

    // Using the ZIO#foldCauseM operator and the Cause#prettyPrint method, implement an operator that takes an effect,
    // and returns a new effect that logs any failures of the original effect (including errors and defects),
    // without changing its failure or success value

    def logFailures[R <: Console, E, A](zio: ZIO[R, E, A]): ZIO[R, E, A] =
      zio.foldCauseM(
        cause => putStrLn(cause.prettyPrint) *> zio,
        _ => zio
      )

    def logFailuresViaTap[R <: Console, E, A](zio: ZIO[R, E, A]): ZIO[R, E, A] =
      zio.tapCause(c => putStrLn(c.prettyPrint))

    // Using the ZIO#foldCauseM method, which “runs” an effect to an Exit value, implement the following function,
    // which will execute the specified effect on any failure at all
    def onAnyFailure[R, E, A](zio: ZIO[R, E, A], handler: ZIO[R, E, Any]): ZIO[R, E, A] =
      zio.foldCauseM(
        _ => handler *> zio,
        _ => zio
      )

    // Using the ZIO#refineWith method, implement the ioException function, which refines the error channel
    // to only include the IOException
    def ioException[R, A](zio: ZIO[R, Throwable, A]): ZIO[R, java.io.IOException, A] =
      zio.refineOrDieWith {
        case ex: IOException => ex
      }(identity)

    // Using the ZIO#refineToOrDie method, narrow the error type of the following effect to just NumberFormatException.
    val parseNumber: ZIO[Any, NumberFormatException, Int] = ZIO.effect("foo".toInt).refineToOrDie

    // Using the ZIO#foldM method, implement the following two functions, which make working with Either values easier,
    // by shifting the unexpected case into the error channel (and reversing this shifting)
    def left[R, E, A, B](zio: ZIO[R, E, Either[A, B]]): ZIO[R, Either[E, B], A] = zio.foldM(
      e => ZIO.fail(Left(e)), {
        case Right(b) => ZIO.fail(Right(b))
        case Left(a)  => ZIO.succeed(a)
      }
    )

    def unleft[R, E, A, B](zio: ZIO[R, Either[E, B], A]): ZIO[R, E, Either[A, B]] = zio.foldM(
      {
        case Right(b) => ZIO.succeed(Right(b))
        case Left(e)  => ZIO.fail(e)
      },
      a => ZIO.succeed(Left(a))
    )

    // Using the ZIO#foldM method, implement the following two functions, which make working with Either values easier,
    // by shifting the unexpected case into the error channel (and reversing this shifting)
    def right[R, E, A, B](zio: ZIO[R, E, Either[A, B]]): ZIO[R, Either[E, A], B] = zio.foldM(
      e => ZIO.fail(Left(e)), {
        case Left(a)  => ZIO.fail(Right(a))
        case Right(b) => ZIO.succeed(b)
      }
    )

    def unright[R, E, A, B](zio: ZIO[R, Either[E, A], B]): ZIO[R, E, Either[A, B]] = zio.foldM(
      {
        case Left(e)  => ZIO.fail(e)
        case Right(a) => ZIO.succeed(Left(a))
      },
      b => ZIO.succeed(Right(b))
    )

    // Using the ZIO#sandbox and ZIO#unsandbox methods, implement the following function
    def catchAllCause[R, E1, E2, A](zio: ZIO[R, E1, A], handler: Cause[E1] => ZIO[R, E2, A]): ZIO[R, E2, A] =
      zio.sandbox.foldM(
        handler,
        v => ZIO.succeed(v)
      )

    // Using the ZIO#foldCauseM method, implement the following function
    def catchAllCauseByFoldM[R, E1, E2, A](zio: ZIO[R, E1, A], handler: Cause[E1] => ZIO[R, E2, A]): ZIO[R, E2, A] =
      zio.foldCauseM(handler, v => ZIO.succeed(v))
  }

}
