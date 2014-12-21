package model

import java.sql.Timestamp

import org.joda.time.DateTime
import Database.profile.simple._
import scala.slick.lifted.{TableQuery, ProvenShape}

case class TinderUser(id: String,
                      name: String,
                      bio: Option[String],
                      distance : Int,
                      commonLikeCount: Int,
                      commonFriendCount: Int,
                      birthDate: String) {

  def apply(`_id`: String,
             `name`: String,
             `bio`: Option[String],
             `distance_mi`: Int,
             `common_like_count`: Int,
             `common_friend_count`: Int,
             `birth_date`: String): Unit = {

    TinderUser(
      id = `_id`,
      name = `name`,
      bio = `bio`,
      distance = `distance_mi`,
      commonLikeCount = `common_like_count`,
      commonFriendCount = `common_friend_count`,
      birthDate = `birth_date`
    )
  }

//    val userFeatures = Seq(
//      if (`bio`.isDefined) 1 else 0,
//      if (`common_like_count` > 0) 1 else 0,
//      if (`common_friend_count` > 0) 1 else 0,
//      if (DateTime.parse(`birth_date`).getMillis > DateTime.parse("05-05-1992").getMillis) 1 else 0
//    )
}

class TinderUsers(tag: Tag) extends Table[TinderUser](tag, "TinderUsers") {
  def id = column[String]("id", O.PrimaryKey)

  def name = column[String]("name", O.NotNull)

  def bio = column[Option[String]]("bio", O.Nullable)

  def distance = column[Int]("distance", O.NotNull)

  def commonLikeCount = column[Int]("common_like_count", O.NotNull)

  def commonFriendCount = column[Int]("common_friend_count", O.NotNull)

  def birthDate = column[String]("birth_date", O.NotNull)

  def createdAt = column[Timestamp]("created_at", O.NotNull)

  def updatedAt = column[Timestamp]("updated_at", O.Nullable)

  override def * = (id, name, bio, distance, commonLikeCount, commonFriendCount, birthDate) <> (TinderUser.tupled, TinderUser.unapply)
}


trait BaseUserDao extends BaseDao with CrudSupport[TinderUsers, Long] {

  val entities: TableQuery[TinderUsers] = TableQuery[TinderUsers]

  def selectBy(entity: TinderUser) = {
    for (e <- entities if e.id === entity.id) yield e
  }

  def selectById(id: Long) = {
    for (e <- entities if e.id === id.toString) yield e
  }
}

class UserDao(implicit innerSession: Session) extends BaseUserDao
