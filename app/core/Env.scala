package core

import scala.util.Try

/**
  * Created by ronnie on 2016. 9. 8..
  */
object Env {

  def as[A](key: String)(implicit ep: EnvParser[A]): Option[A] =
    ep.parse(System.getProperty(key))
}

// string -> A
trait EnvParser[A] {
  def parse(s: String): Option[A]
}

object EnvParserInstance {
  implicit val intEnvParser = new EnvParser[Int] {
    override def parse(s: String): Option[Int] = Try {
      s.toInt
    }.toOption
  }

  implicit val strEnvParser = new EnvParser[String] {
    override def parse(s: String): Option[String] = Try {
      s
    }.toOption
  }

  implicit val booleanEnvParser = new EnvParser[Boolean] {
    override def parse(s: String): Option[Boolean] = Try {
      s.toBoolean
    }.toOption
  }
}