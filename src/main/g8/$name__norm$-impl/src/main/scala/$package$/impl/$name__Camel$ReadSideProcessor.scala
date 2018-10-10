package $package$.impl

import akka.Done
import com.datastax.driver.core.PreparedStatement
import com.lightbend.lagom.scaladsl.persistence.ReadSideProcessor.ReadSideHandler
import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession}
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, EventStreamElement, ReadSideProcessor}
import org.slf4j.LoggerFactory
import scala.concurrent.{ExecutionContext, Future}

class $name;format="Camel"$ReadSideProcessor(readSide: CassandraReadSide, session: CassandraSession)
                           (implicit ec: ExecutionContext)
  extends ReadSideProcessor[$name;format="Camel"$Event] {

  private val log = LoggerFactory.getLogger(classOf[$name;format="Camel"$ReadSideProcessor])

  // Cassandra optimization: prepared statement
  private var insert$name;format="Camel"$Statement: PreparedStatement = _
  private var delete$name;format="Camel"$Statement: PreparedStatement = _
  private var insertSessionStatement: PreparedStatement = _
  private var deleteSessionStatement: PreparedStatement = _
  private var update$name;format="Camel"$StatusStatement: PreparedStatement = _


  def buildHandler: ReadSideHandler[$name;format="Camel"$Event] = {
    readSide.builder[$name;format="Camel"$Event]("$name;format="camel"$Offset")
      .setGlobalPrepare(createTable)
      .setPrepare { tag => prepareStatements()}
      // React on listened Event "$name;format="Camel"$Created" and then call function $name;format="camel"$Created
      .setEventHandler[$name;format="Camel"$Created]($name;format="camel"$Created)
      .setEventHandler[$name;format="Camel"$Deleted]($name;format="camel"$Deleted)
      .setEventHandler[AccessTokenGranted](accessTokenGranted)
      .setEventHandler[AccessTokenRevoked](accessTokenRevoked)
      .setEventHandler[$name;format="Camel"$Verified]($name;format="camel"$Verified)
      .setEventHandler[$name;format="Camel"$UnVerified]($name;format="camel"$Unverified)
      .build()
  }

  private def createTable(): Future[Done] = {
    for {
      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS $name;format="camel"$s (
          |   id text, $name;format="camel"$name text, email text, status text,
          |   PRIMARY KEY (id)
          |   )
        """.stripMargin)

      //_ <- session.executeWrite(
      //  """
      //    |ALTER TABLE $name;format="camel"$s ADD email text
      //  """.stripMargin)

      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS sessions (
          |   access_token text, refresh_token text, $name;format="camel"$id text,
          |   PRIMARY KEY (access_token)
          |   )
        """.stripMargin
      )
    } yield Done
  }

  private def prepareStatements(): Future[Done] = {
    for {
      insert$name;format="Camel"$ <- session.prepare(
        """
          |INSERT INTO $name;format="camel"$s
          |(id, $name;format="camel"$name, email, status)
          |VALUES (?, ?, ?, ?)
        """.stripMargin
      )
      verify$name;format="Camel"$ <- session.prepare(
        """
          |UPDATE $name;format="camel"$s
          |SET status = ?
          |WHERE id = ?
        """.stripMargin
      )
      insertSession <- session.prepare(
        """
          |INSERT INTO sessions
          |(access_token, refresh_token, $name;format="camel"$id)
          |VALUES (?, ?, ?)
        """.stripMargin
      )
      deleteSession <- session.prepare(
        """
          |DELETE
          |FROM sessions
          |WHERE access_token = ?
        """.stripMargin
      )
      delete$name;format="Camel"$ <- session.prepare(
        """
          |DELETE
          |FROM $name;format="camel"$s
          |WHERE id = ?
        """.stripMargin
      )
    } yield {
      insert$name;format="Camel"$Statement = insert$name;format="Camel"$
      delete$name;format="Camel"$Statement = delete$name;format="Camel"$
      insertSessionStatement = insertSession
      deleteSessionStatement = deleteSession
      update$name;format="Camel"$StatusStatement = verify$name;format="Camel"$
      Done
    }
  }

  private def $name;format="camel"$Created(e: EventStreamElement[$name;format="Camel"$Created]) = {
    log.info("$name;format="Camel"$ReadSideProcessor received a $name;format="Camel"$CreatedEvent")
    Future.successful {
      val u = e.event
      List(insert$name;format="Camel"$Statement.bind(
        u.$name;format="camel"$Id.toString,
        u.$name;format="camel"$name,
        u.email,
        u.status.toString
      ))
    }
  }

  private def accessTokenGranted(e: EventStreamElement[AccessTokenGranted]) = {
    Future.successful {
      val s = e.event.session
      List(insertSessionStatement.bind(
        s.access_token.toString,
        s.refresh_token.toString,
        e.event.$name;format="camel"$Id.toString
      ))
    }
  }

  private def accessTokenRevoked(e: EventStreamElement[AccessTokenRevoked]) = {
    Future.successful {
      val u = e.event
      List(deleteSessionStatement.bind(
        u.access_token.toString
      ))
    }
  }

  private def $name;format="camel"$Verified(e: EventStreamElement[$name;format="Camel"$Verified]) = {
    Future.successful {
      val u = e.event
      List(update$name;format="Camel"$StatusStatement.bind(
        $name;format="Camel"$Status.VERIFIED.toString,
        u.$name;format="camel"$Id.toString
      ))
    }
  }

  private def $name;format="camel"$Unverified(e: EventStreamElement[$name;format="Camel"$UnVerified]) = {
    Future.successful {
      val u = e.event
      List(update$name;format="Camel"$StatusStatement.bind(
        $name;format="Camel"$Status.UNVERIFIED.toString,
        u.$name;format="camel"$Id.toString
      ))
    }
  }

  private def $name;format="camel"$Deleted(e: EventStreamElement[$name;format="Camel"$Deleted]) = {
    Future.successful {
      val u = e.event
      List(delete$name;format="Camel"$Statement.bind(
        u.$name;format="camel"$Id.toString
      ))
    }
  }

  override def aggregateTags: Set[AggregateEventTag[$name;format="Camel"$Event]] = $name;format="Camel"$Event.Tag.allTags
}
