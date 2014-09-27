import scala.concurrent.duration._
import dispatch._
import dispatch.Defaults._
import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import scala.concurrent.Await

class TinderBot {

  def requestAuthenticationToken(fbId: String, fbToken: String) = {
    val bodyJs = compact(render(("facebook_token" -> fbToken) ~ ("facebook_id" -> fbId)))
    val req = url("https://api.gotinder.com/auth").POST
              .setBody(bodyJs)
              .setContentType("application/json", "UTF-8")
              .secure

    val response = Http(req OK as.String)
    Await.result(response, 10 seconds)

    val tokenJs = parse(response()) \\ "token"
    println(compact(render(tokenJs)))
    compact(render(tokenJs))
  }

  def getRecommendations(tinderAuthToken: String) = {
    val req = url("https://api.gotinder.com/user/recs").GET
      .addHeader("X-Auth-Token", tinderAuthToken)
      .setContentType("application/json", "UTF-8")
      .secure

    val response = Http(req OK as.String)
    Await.result(response, 10 seconds)

    val results = parse(response()) \\ "results"

    println(compact(render(results)))
    compact(render(results))
  }

}

object TinderBot {

  def main(args: Array[String]) {
    println("Initiating the tinder bandit!")

    val tinderBot = new TinderBot
    //    val tinderAuthToken = tinderBot.requestAuthenticationToken(fbId, fbToken)
    val tinderAuthToken = "af221eba-8ddd-46f1-9fdb-e7499c7f9d70"

    for {
      rec <- tinderBot.getRecommendations(tinderAuthToken)
    } yield {
      rec
    }

  }
}


