package bandit

import model.{Arms, Arm}

class LinUCBBandit(arms: Seq[Arm]) extends BaseBandit {

  val alpha = 0.2

  def apply = {
    //load arms
  }


  protected def getSelection(contextVector: Vector): Selection = {
    arms.map {
      // get arm features (B)
      // do np.outer to get M


      // pass both and new feature into ucb

      ucb
    }.reduce{ (a, b) =>
      if (a.value < b.value) {
        b
      } else {
        a
      }
    }
  }

  protected def ucb(arm: Arm): Selection = {
    10L
  }

  protected def updateAlgorithm(arm: Arm, value: Double): Long = {
    // look up arm features in db

    // update arm features (reward * context vector)

    // store new arm features
  }
}
