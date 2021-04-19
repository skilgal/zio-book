package book.zio.chapter.four

import zio.test.DefaultRunnableSpec

object ErrorModelSpec extends DefaultRunnableSpec {

  def specFailWithMessage = suite("test fail with message")(
    testM("This will ensure no error is thrown") {
      assertM(ErrorModel.failWithMessage("Test string"))
    }
  )

}
