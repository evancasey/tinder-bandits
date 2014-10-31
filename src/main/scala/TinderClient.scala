import dispatch._
import dispatch.Defaults._
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.Await
import net.liftweb.json._
import net.liftweb.json.JsonDSL._

class TinderClient(fbId: String, fbToken: String) {

  private val authToken = {
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

  def tinderGet(route: String) = {
    val req = url("https://api.gotinder.com/" + route).GET
      .addHeader("X-Auth-Token", authToken)
      .setContentType("application/json", "UTF-8")
      .secure

    Await.result(Http(req OK as.String), 10 seconds)
  }

  def tinderPost(route: String, bodyJs: String) = {
    val req = url("https://api.gotinder.com/" + route).POST
      .addHeader("X-Auth-Token", authToken)
      .setBody(bodyJs)
      .setContentType("application/json", "UTF-8")
      .secure

    Await.result(Http(req OK as.String), 10 seconds)
  }
}
