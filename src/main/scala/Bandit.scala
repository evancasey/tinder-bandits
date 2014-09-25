//abstract class Bandit(arms: Seq[Arm]) {
//
//  /**
//   * Choose the "best" arm
//   */
//  protected def chooseArm(): Arm
//
//  /**
//   * User-facing method for selecting the next arm to use
//   */
//  def selectArm(arm: Arm) = {
//    val arm = chooseArm()
//    arm.incrementPullCount
//
//  }
//
//  /**
//   * User-facing method to be called when an arm is pulled
//   */
//  def update(selection: Selection) = {
//    arm.incrementRewardCount
//  }
//
//
//  protected def updateAlgo(arm: Arm ) = {
//
//  }
//
//
//}
