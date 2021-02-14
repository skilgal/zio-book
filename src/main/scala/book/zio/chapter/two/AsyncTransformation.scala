package book.zio.chapter.two

import zio._

object AsyncTransformation {
  // Using ZIO.effectAsync, convert the following asynchronous, callback- based function into a ZIO function:
  def getCacheValue(
    key: String,
    onSuccess: String => Unit,
    onFailure: Throwable => Unit
  ): Unit = ???

  def getCacheValueZio(key: String): ZIO[Any, Throwable, String] =
    ZIO.effectAsync { callback =>
      getCacheValue(key, str => callback(ZIO.succeed(str)), err => callback(ZIO.fail(err)))
    }

  // Using ZIO.effectAsync, convert the following asynchronous, callback-based function into a ZIO function:
  trait User

  def saveUserRecord(
    user: User,
    onSuccess: () => Unit,
    onFailure: Throwable => Unit
  ): Unit = ???

  def saveUserRecordZio(user: User): ZIO[Any, Throwable, Unit] =
    ZIO.effectAsync { cb =>
      saveUserRecord(
        user,
        () => cb(ZIO.unit),
        ec => cb(ZIO.fail(ec))
      )
    }

  // Using ZIO.fromFuture, convert the following code to ZIO:
  import scala.concurrent.{ ExecutionContext, Future }
  trait Query
  trait Result
  def doQuery(query: Query)(implicit ec: ExecutionContext): Future[Result] =
    ???
  def doQueryZio(query: Query): ZIO[Any, Throwable, Result] = ???

}
