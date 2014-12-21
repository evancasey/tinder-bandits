package model

import scala.slick.driver.JdbcProfile
import Database.profile.simple._

class BaseDao(implicit innerSession: Session) {

  val profile: JdbcProfile = Database.profile
  val session: Session = innerSession

}
