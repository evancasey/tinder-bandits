package bandit

import model.Arm
import org.scalatest._
import Matchers._

/**
 * Class to test the Lin UCB Bandit algorithm
 */
class LinUCBBanditSpec extends WordSpec {

  val arm1 = Arm(
    id = 1L,
    message = "hey babe",
    pullCount = 0,
    requestCount = 0,
    value = 0.0
  )

  val arm2 = Arm(
    id = 2L,
    message = "how's it going?",
    pullCount = 0,
    requestCount = 0,
    value = 0.0
  )

  "LinUCBBandit" should {
    "select the 'best' arm correctly" in {
      val bandit = new LinUCBBandit(Seq(arm1, arm2))
      val selection = bandit.selectArm

      selection.id shouldEqual arm1.id
    }

    "update an arm correctly" in {
      val bandit = new LinUCBBandit(Seq(arm1, arm2))
      val selection = bandit.selectArm
      val updatedArmId = bandit.update(selection)

      updatedSelection shouldEqual 5.0
    }
  }

}
