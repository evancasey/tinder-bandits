package model

import com.mchange.v2.c3p0.ComboPooledDataSource
import scala.slick.driver.{JdbcProfile,MySQLDriver}

object Database extends Profile {

  val profile: JdbcProfile = MySQLDriver

  private final val host = "jdbc:mysql://localhost/tinder"
  private final val username = "root"
  private final val password = ""
  private final val driver = "com.mysql.jdbc.Driver"

  val databasePool = {

    val ds = new ComboPooledDataSource
    ds.setDriverClass(driver)
    ds.setJdbcUrl(host)
    ds.setUser(username)
    ds.setPassword(password)
    ds.setMinPoolSize(5)
    ds.setAcquireIncrement(5)
    ds.setMaxPoolSize(100)
    MySQLDriver.simple.Database.forDataSource(ds)
  }
}

