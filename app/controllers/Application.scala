package controllers

import core.EnvParserInstance._
import core.Env
import play.api.Logger
import play.api.Play.current
import play.api.libs.iteratee.Iteratee
import play.api.libs.oauth.{ConsumerKey, OAuthCalculator, RequestToken}
import play.api.libs.ws.WS
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * Created by ronnie on 2016. 8. 30..
  */
object Application extends Controller {

  def index = Action.async { implicit request =>
    val loggingIteratee = Iteratee.foreach[Array[Byte]] {
      (array: Array[Byte]) => Logger.info(">>" + array.map(_.toChar).mkString)
    }

    credential().map { case (ck: ConsumerKey, rt: RequestToken) => {
      WS.url("https://stream.twitter.com/1.1/statuses/filter.json")
        .sign(OAuthCalculator(ck, rt))
        .withQueryString("track" -> "reactive")
        .get { response => {
          Logger.info("response status: " + response.status)
          loggingIteratee
        }
        }.map(_ => Ok("Stream closed"))
    }}.getOrElse {
      Future.successful {
        InternalServerError("Twitter credentials missing")
      }
    }
  }

  def credential(): Option[(ConsumerKey, RequestToken)] = {
    val apiKey: Option[String] = Env.as[String]("apiKey")
    val apiSecret: Option[String] = Env.as[String]("apiSecret")
    val token: Option[String] = Env.as[String]("token")
    val tokenSecret: Option[String] = Env.as[String]("tokenSecret")
    
    // 1. option에서 값을 꺼내서 option(tuple)로 변환
    // 1.1. flatMap
    val flatMapTuple: Option[(ConsumerKey, RequestToken)] = apiKey.flatMap {
      ak => apiSecret.flatMap {
        as => token.flatMap {
          t => tokenSecret.flatMap {
            ts => Some(ConsumerKey(ak, as), RequestToken(t, ts))
          }
        }
      }
    }
    
    // 1.2 match case
    val matchTuple: Option[(ConsumerKey, RequestToken)] = apiKey match {
      case Some(ak) => apiSecret match {
        case Some(as) => token match {
          case Some(t) => tokenSecret match {
            case Some(ts) => Some((ConsumerKey(ak, as), RequestToken(t, ts)))
            case None => None
          }
          case None => None
        }
        case None => None
      }
      case None => None
    }

    val matchTuple2: Option[(ConsumerKey, RequestToken)] = (apiKey, apiSecret, token, tokenSecret) match {
      case (Some(ak), Some(as), Some(t), Some(ts)) => Some((ConsumerKey(ak, as), RequestToken(t, ts)))
      case _ => None
    }

    // 1.3 for comprehension
    val forTuple: Option[(ConsumerKey, RequestToken)] = for {
      ak <- apiKey
      as <- apiSecret
      t <- token
      ts <- tokenSecret
    } yield (ConsumerKey(ak, as), RequestToken(t, ts))
    
    // 1.4 map flatten
    val mapTuple: Option[(ConsumerKey, RequestToken)] = apiKey.flatMap {
      ak => apiSecret.flatMap {
        as => token.flatMap {
          t => tokenSecret.map {
            ts => (ConsumerKey(ak, as), RequestToken(t, ts))
          }
        }
      }
    }
    
    forTuple
  }
}