import java.util.Random

abstract class BaseBandit(arms: Seq[Arm]) {

  lazy val rand = new Random()
  lazy val armCount = arms.size
  protected val armIndexFromId = Map[String, Int]((0 until armCount).map(i => arms(i).id -> i):_*)
  protected def isValidId(id: String): Boolean = armIndexFromId.keySet.contains(id)

  protected def chooseArm(): Arm

  def selectArm(arm: Arm): Selection = {
    val arm = chooseArm()
    arm.incrementRequestCount()
    Selection(arm.id)
  }

  def update(selection: Selection) = {
    val arm = arms(armIndexFromId(selection.id))
    arm.incrementPullCount
    updateAlgorithm(arm, selection.value)

  }

  protected def updateAlgorithm(arm: Arm, value: Double): Boolean

  def printInfo() {
    println("Arms:   " + arms.map(i=>i.id).mkString(", "))
    println("Pulls:  " + arms.map(i=>i.pullCount.toString).mkString(", "))
    println("Values: " + arms.map(i=>i.value.toString).mkString(", "))
  }

}
