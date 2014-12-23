package bandit

import java.util.Random
import model.{ArmDao, Arm}

abstract class BaseBandit {

  val dao = new ArmDao
  lazy val rand = new Random()

  /**
   *
   * @return
   */
  protected def getSelection: Selection

  /**
   *
   * @return
   */
  def selectArm: Arm = {
    val selection = getSelection
    dao.selectById(selection.id).head
  }

  /**
   *
   * @param selection
   */
  def update(selection: Selection): Unit = {
    val arm = dao.selectById(selection.id).head
    dao.incrementPullCount(arm.id)
    updateAlgorithm(arm, selection.value)
  }


  /**
   *
   * @param arm
   * @param value
   * @return id of the arm being updated
   */
  protected def updateAlgorithm(arm: Arm, value: Double): Long


//  def printInfo() {
//    println("Arms:   " + arms.map(i=>i.id).mkString(", "))
//    println("Pulls:  " + arms.map(i=>i.pullCount.toString).mkString(", "))
//    println("Values: " + arms.map(i=>i.value.toString).mkString(", "))
//  }

}
