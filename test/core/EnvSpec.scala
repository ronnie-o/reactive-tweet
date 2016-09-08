package core

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by ronnie on 2016. 9. 8..
  */
class EnvSpec extends FunSuite with Matchers {
  test("apiKey") {
    import EnvParserInstance._
    Env.as[Int]("apiKey") shouldBe Some(123)
    Env.as[String]("apiToken") shouldBe Some("abc")
    Env.as[Boolean]("hasKey") shouldBe Some(true)
  }
}