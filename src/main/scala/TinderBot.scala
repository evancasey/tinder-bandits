import net.liftweb.json._

class TinderBot(tinderClient: TinderClient) {

  val client = tinderClient

  def getRecommendation = {
    implicit val formats = net.liftweb.json.DefaultFormats

    val response = tinderClient.tinderGet("user/recs")
    val results = (parse(response) \\ "results").children

    println("Getting your recommendations:" )
    results.map(_.extract[TinderUser]).head
  }


  def like(user: TinderUser) = {
    val response = tinderClient.tinderGet("like/" + user.`_id`)
    val results = parse(response)

    println("Liking user: " + user.`_id`)
  }

  def respondAll = {
    val updates = tinderClient.tinderPost("updates","some-date")
    for (update <- updates) {

    }

  }

  def message(user: TinderUser) = {
    val response = tinderClient.tinderPost("user/matches" + user.`_id`, "call bandit here")
    val results = parse(response)
  }

}

object TinderBot {

  def main(args: Array[String]) {
    println("Initiating the tinder bandit!")

    val tinderClient = new TinderClient(args(0), args(1))
    val tinderBot = new TinderBot(tinderClient)

    while(true) {
      val user = tinderBot.getRecommendation
      println(user)
//      tinderBot.like(user)
//      tinderBot.respondAll
      Thread.sleep(5000)
    }


  }
}


