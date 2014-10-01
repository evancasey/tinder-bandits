import dispatch._
import dispatch.Defaults._
import scala.concurrent.Await
import scala.concurrent.duration._
import net.liftweb.json._
import net.liftweb.json.JsonDSL._

class TinderBot(fbId: String, fbToken: String) {

  implicit val formats = DefaultFormats

  private val authToken = requestAuthenticationToken

  private def requestAuthenticationToken = {

    val bodyJs = compact(render(("facebook_token" -> fbToken) ~ ("facebook_id" -> fbId)))
    val req = url("https://api.gotinder.com/auth").POST
              .setBody(bodyJs)
              .setContentType("application/json", "UTF-8")
              .secure

    val response = Http(req OK as.String)
    Await.result(response, 10 seconds)

    val tokenJs = parse(response()) \\ "token"
    val authToken = compact(render(tokenJs)).replace("\"", "")

    println("Acquired Tinder Auth Token: " + compact(render(authToken)))
    authToken
  }

  private def tinderGet(route: String) = {
    val req = url("https://api.gotinder.com/" + route).GET
      .addHeader("X-Auth-Token", authToken)
      .setContentType("application/json", "UTF-8")
      .secure

    val response = Http(req OK as.String)
    Await.result(response, 10 seconds)
    response
  }

  def getRecommendations = {
    val response = tinderGet("user/recs")
    val recsJs = (parse(response()) \ "response").extractOpt[String]

    val x = 1



//    println("Getting your recommendations:" + compact(render(recs)))
//    compact(render(recs))
    List(1)
  }

}

object TinderBot {

  def main(args: Array[String]) {
    println("Initiating the tinder bandit!")

    val tinderBot = new TinderBot(args(0), args(1))

    for {
      rec <- tinderBot.getRecommendations
    } yield {
      rec
    }

  }
}


