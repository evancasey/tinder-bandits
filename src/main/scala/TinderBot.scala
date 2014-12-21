import bandit.LinUCBBandit
import model.{Database, TinderUser, UserDao}
import net.liftweb.json._

class TinderBot(tinderClient: TinderClient, userDao: UserDao, bandit: LinUCBBandit) {

  val client = tinderClient
  val dao = userDao

  def getRecommendation: TinderUser = {
    implicit val formats = net.liftweb.json.DefaultFormats

    val response = tinderClient.tinderGet("user/recs")
    val results = (parse(response) \\ "results").children

    println("Getting your recommendations:" )
    results.map(_.extract[TinderUser]).head
  }


  def like(user: TinderUser) = {
    val response = tinderClient.tinderGet("like/" + user.id)
    val results = parse(response)

    println("Liking user: " + user.id)
  }

  def respondAll = {
    val updates = tinderClient.tinderPost("updates","some-date")
    for (update <- updates) {

    }

  }

  def message(user: TinderUser) = {
    val response = tinderClient.tinderPost("user/matches" + user.id, "call bandit here")
    val results = parse(response)
  }

}

object TinderBot {

  def main(args: Array[String]) {
    println("Initiating the tinder bandit!")

    implicit val session = Database.databasePool.createSession()

    val tinderClient = new TinderClient(args(0), args(1))
    val userDao = new UserDao
    val bandit = LinUCBBandit()

    val tinderBot = new TinderBot(tinderClient, userDao, bandit)

    while(true) {
      val user = tinderBot.getRecommendation

      userDao.insert(user)

      println(user)
//      tinderBot.like(user)
//      tinderBot.respondAll
      Thread.sleep(5000)
    }

  }
}


