package book.zio.chapter.two

object ToyZio {
  final case class ZIO[-R, +E, +A](run: R => Either[E, A])

  // Implement the zipWith function in terms of the toy model of a ZIO effect.
  // The function should return an effect that sequentially composes the specified effects,
  // merging their results with the specified user-defined function.
  def zipWith[R, E, A, B, C](self: ZIO[R, E, A], that: ZIO[R, E, B])(f: (A, B) => C): ZIO[R, E, C] = ???

  // Implement the collectAll function in terms of the toy model of a ZIO effect.
  // The function should return an effect that sequentially collects the results of the specified collection of effects.
  def collectAll[R, E, A](
    in: Iterable[ZIO[R, E, A]]
  ): ZIO[R, E, List[A]] = ???

  // Implement the foreach function in terms of the toy model of a ZIO effect.
  // The function should return an effect that sequentially runs the specified function on every element of the
  def foreach[R, E, A, B](in: Iterable[A])(f: A => ZIO[R, E, B]): ZIO[R, E, List[B]] = ???

  // Implement the orElse function in terms of the toy model of a ZIO effect.
  // The function should return an effect that tries the left hand side, but if that effect fails,
  // it will fallback to the effect on the right hand side.
  def orElse[R, E1, E2, A](self: ZIO[R, E1, A], that: ZIO[R, E2, A]): ZIO[R, E2, A] = ???

  // Using ZIO.fail and ZIO.succeed, implement the following function, which converts an Either into a ZIO effect:
  def eitherToZIO[E, A](either: Either[E, A]): ZIO[Any, E, A] = ???

  // Using ZIO.fail and ZIO.succeed, implement the following function, which converts a List into a ZIO effect,
  // by looking at the head element in the list and ignoring the rest of the elements.
  def listToZIO[A](list: List[A]): ZIO[Any, None.type, A] = ???

}
