package io.digitalcat.publictransportation.services.identity.impl

import akka.Done
import java.sql.Timestamp
import java.util.UUID

import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession
import io.digitalcat.publictransportation.services.common.date.DateUtcUtil

import scala.concurrent.{ExecutionContext, Future}

class IdentityRepository(db: CassandraSession)(implicit ec: ExecutionContext) {

  def createTable(): Future[Done] = {
    db.executeCreateTable(
      """
        |CREATE TABLE IF NOT EXISTS users(
        |  id              uuid,
        |  client_id       uuid,
        |  username        varchar,
        |  email           varchar,
        |  first_name      varchar,
        |  last_name       varchar,
        |  hashed_password varchar, PRIMARY KEY (id)
        |);
      """.stripMargin)

    db.executeCreateTable(
      """
        |CREATE TABLE IF NOT EXISTS users_by_username(
        |  id              uuid,
        |  client_id       uuid,
        |  username        varchar,
        |  email           varchar,
        |  first_name      varchar,
        |  last_name       varchar,
        |  hashed_password varchar,
        |  PRIMARY KEY (username, id)
        |);
      """.stripMargin)

    db.executeCreateTable(
      """
        |CREATE TABLE IF NOT EXISTS reserved_usernames(
        |    username varchar,
        |    user_id  uuid,
        |    created_on timestamp, PRIMARY KEY (username)
        |);
      """.stripMargin)

    db.executeCreateTable(
      """
        |CREATE TABLE IF NOT EXISTS reserved_emails(
        |    email    varchar,
        |    user_id  uuid,
        |    created_on timestamp, PRIMARY KEY (email)
        |);
      """.stripMargin)
  }

  def findUserByUsername(username: String): Future[Option[UserByUsername]] = {
    val result = db.selectOne("SELECT id, client_id, username, hashed_password FROM users_by_username WHERE username = ?", username).map {
      case Some(row) => Option(
        UserByUsername(
          username = row.getString("username"),
          id = row.getUUID("id"),
          clientId = row.getUUID("client_id"),
          hashedPassword = row.getString("hashed_password")
        )
      )
      case None => Option.empty
    }

    result
  }

  def reserveUsername(username: String): Future[Boolean] = {
    val createdOn = new Timestamp(DateUtcUtil.now().getMillis)

    db.selectOne("INSERT INTO reserved_usernames (username, created_on) VALUES (?, ?) IF NOT EXISTS", username, createdOn).map {
      case Some(row) => row.getBool("[applied]")
      case None => false
    }
  }

  def unreserveUsername(username: String) = {
    db.executeWrite("DELETE FROM reserved_usernames WHERE username = ?", username)
  }

  def reserveEmail(email: String): Future[Boolean] = {
    val createdOn = new Timestamp(DateUtcUtil.now().getMillis)

    db.selectOne("INSERT INTO reserved_emails (email, created_on) VALUES (?, ?) IF NOT EXISTS", email, createdOn).map {
      case Some(row) => row.getBool("[applied]")
      case None => false
    }
  }

  def unreserveEmail(email: String) = {
    db.executeWrite("DELETE FROM reserved_emails WHERE email = ?", email)
  }
}

case class UserByUsername(username: String, id: UUID, clientId: UUID, hashedPassword: String)
