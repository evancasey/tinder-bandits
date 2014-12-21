package model

import java.sql.Timestamp

import org.joda.time.DateTime
import Database.profile.simple._
import scala.slick.lifted.{TableQuery, ProvenShape}

case class Arm(id: Long,
               message: String,
               pullCount: Int,
               requestCount: Int,
               value: Double) {

  override def toString() = {
    Seq[String](id.toString, pullCount.toString, requestCount.toString, value.toString).mkString(",")
  }
}

class Arms(tag: Tag) extends Table[Arm](tag, "Arms") {
  def id = column[String]("id", O.PrimaryKey)

  def pullCount = column[Int]("pull_count", O.NotNull)

  def requestCount = column[Int]("request_count", O.NotNull)

  def value = column[Int]("value", O.NotNull)

  def createdAt = column[Timestamp]("created_at", O.NotNull)

  def updatedAt = column[Timestamp]("updated_at", O.Nullable)

  override def * = (id, pullCount, requestCount, value) <> (Arm.tupled, Arm.unapply)
}


trait BaseArmDao extends BaseDao with CrudSupport[Arms, Long] {

  val entities: TableQuery[Arms] = TableQuery[Arms]

  def selectBy(entity: Arm): List[Arm] = {
    (for {
      e <- entities if e.id === entity.id
    } yield e).list
  }

  def selectById(id: Long): List[Arm] = {
    (for {
      e <- entities if e.id === id.toString
    } yield e).list
  }

  def incrementPullCount(id: Long): Unit = {
    val arm = selectById(id).head
    val incremented = arm.copy(pullCount = arm.pullCount + 1)
    update(incremented)
  }

  def incrementRequestCount(id: Long): Unit = {
    val arm = selectById(id).head
    val incremented = arm.copy(requestCount = arm.requestCount + 1)
    update(incremented)
  }

  def incrementValue(id: Long, v: Double): Unit = {
    val arm = selectById(id).head
    val incremented = arm.copy(value = arm.value + v)
    update(incremented)
  }

}

class ArmDao(implicit innerSession: Session) extends BaseArmDao
