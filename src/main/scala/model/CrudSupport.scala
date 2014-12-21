package model

import scala.slick.lifted.{TableQuery, AbstractTable}
import Database.profile.simple._

trait CrudSupport[T <: AbstractTable[_], K] extends Insertable[T]
  with Searchable[T, K]
  with Updatable[T, K]
  with Removable[T, K]
  with Selectable[T, K]

sealed trait Requestable[T <: AbstractTable[_]] extends Profile {
  val entities: TableQuery[T]
  val session: Session
  implicit val innerSession = session
}

trait Selectable[T <: AbstractTable[_], K] {
  def selectBy(entity: T#TableElementType): Query[T, T#TableElementType]

  def selectById(id: K): Query[T, T#TableElementType]
}

trait Insertable[T <: AbstractTable[_]] extends Requestable[T] {
  def insert(entity: T#TableElementType) {
    entities += entity
  }

  def insertAll(data: T#TableElementType*) {
    data.foreach(insert)
  }
}

trait Updatable[T <: AbstractTable[_], K] extends Requestable[T] {
  this: Selectable[T, K] =>

  def update(entity: T#TableElementType) {
    selectBy(entity).update(entity)
  }

  def update(id: K, entity: T#TableElementType) {
    selectById(id).update(entity)
  }
}

trait Removable[T <: AbstractTable[_], K] extends Requestable[T] {
  this: Selectable[T, K] =>

  def delete(entity: T#TableElementType) {
    selectBy(entity).mutate(_.delete())
  }

  def deleteById(id: K) {
    selectById(id).mutate(_.delete())
  }
}

trait Searchable[T <: AbstractTable[_], K] extends Requestable[T] {
  this: Selectable[T, K] =>

  def findAll(): List[T#TableElementType] = {
    entities.list
  }

  def findPage(pageNumber: Int, pageSize: Int): List[T#TableElementType] = {
    entities.drop(pageSize * (pageNumber - 1)).take(pageSize).list
  }

  def find(limit: Int): List[T#TableElementType] = {
    entities.take(limit).list
  }

  def findById(id: K): T#TableElementType = {
    selectById(id).first()
  }

  def count: Int = {
    Query(entities.length).first()
  }

}
