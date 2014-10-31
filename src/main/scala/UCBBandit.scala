class UCBBandit(arms: Seq[Arm]) extends BaseBandit(arms) {

  def chooseArm(): Arm = {

  }

  protected def updateAlgorithm(arm: Arm, value: Double): Boolean = {
    try {
      arm.incrementValue(value)
      true
    }
    catch {
      case _ => false
    }
  }

}
