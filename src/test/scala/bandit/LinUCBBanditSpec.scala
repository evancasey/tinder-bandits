package bandit

import org.scalatest._
import Matchers._

/**
 * Class to test the Lin UCB Bandit algorithm
 */
class LinUCBBanditSpec extends WordSpec {

  val arm1 = Arm(
    id = "1",
    initPullCount = 0,
    initRequestCount = 0,
    initValue = 0.0
  )

  val arm2 = Arm(
    id = "2",
    initPullCount = 0,
    initRequestCount = 0,
    initValue = 0.0
  )

  "LinUCBBandit" should {
    "select the 'best' arm correctly" in {
      val bandit = LinUCBBandit(Seq(arm1, arm2))
      val selection = bandit.selectArm

      selection.id shouldEqual arm1.id
    }

    "update an arm correctly" in {
      val bandit = LinUCBBandit(Seq(arm1, arm2))
      val selection = bandit.selectArm
      val updatedArmId = bandit.update(selection)

      updatedSelection shouldEqual 5.0
    }
  }

}
