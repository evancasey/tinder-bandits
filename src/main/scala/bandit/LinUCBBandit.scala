package bandit

import model.Arm

case class LinUCBBandit extends BaseBandit {


  def chooseArm: Arm = {

  }

  protected def updateAlgorithm(arm: Arm, value: Double): Long = {
    dao.incrementValue(arm.id, arm.value)

  }
}
