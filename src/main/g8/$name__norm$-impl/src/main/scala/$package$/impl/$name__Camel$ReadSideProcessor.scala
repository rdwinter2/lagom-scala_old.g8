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

//  private val log = LoggerFactory.getLogger(classOf[$name;format="Camel"$ReadSideProcessor])

  // Cassandra optimization: prepared statement
//  private var insert$name;format="Camel"$Statement: PreparedStatement = _
//  private var delete$name;format="Camel"$Statement: PreparedStatement = _
//  private var insertSessionStatement: PreparedStatement = _
//  private var deleteSessionStatement: PreparedStatement = _
//  private var update$name;format="Camel"$StatusStatement: PreparedStatement = _


  def buildHandler: ReadSideHandler[$name;format="Camel"$Event] = {
    readSide.builder[$name;format="Camel"$Event]("$name;format="camel"$EventOffset")
      .setGlobalPrepare( () => createTables() )
      .setPrepare { _ => prepareStatements() }
      .setEventHandler[$name;format="Camel"$CreatedEvent]($name;format="camel"$CreatedEventDML)
      .build()
  }

  override def aggregateTags: Set[AggregateEventTag[$name;format="Camel"$Event]] =
    $name;format="Camel"$Event.Tag.allTags

  private def createTables(): Future[Done] = {
    for {
      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS $name;format="snake,upper"$(
          |  $name;format="snake,upper"$ID      varchar,
          |  NATURAL_KEY                        varchar,
          |  DATA                               varchar
          |  PRIMARY KEY ($name;format="snake,upper"$ID)
          |);
        """.stripMargin)

      _ <- session.executeCreateTable(
        """
          |CREATE TABLE IF NOT EXISTS $name;format="snake,upper"$_BY_NATURAL_KEY(
          |  NATURAL_KEY                        varchar,
          |  $name;format="snake,upper"$ID      varchar
          |  PRIMARY KEY (NATURAL_KEY)
          |);
        """.stripMargin)
    } yield Done
  }

  private var $name;format="camel"$CreatedEventDMLStatement: PreparedStatement = _
  private var $name;format="camel"$CreatedEventByNaturalKeyDMLStatement: PreparedStatement = _
  private def prepareStatements(): Future[Done] = {
    for {
       insert$name;format="Camel"$ <- session.prepare(
        """insert$name;format="Camel"$
          |INSERT INTO $name;format="snake,upper"$(
          |  $name;format="snake,upper"$ID,
          |  NATURAL_KEY,
          |  DATA
          |  ) VALUES (
          |  ?,
          |  ?,
          |  ?
          |  );
        """.stripMargin)
       insert$name;format="Camel"$ByNaturalKey <- session.prepare(
        """
          |INSERT INTO $name;format="snake,upper"$_BY_NATURAL_KEY(
          |  NATURAL_KEY,
          |  $name;format="snake,upper"$ID
          |  ) VALUES (
          |  ?,
          |  ?
          |  );
        """.stripMargin)
    } yield {
      $name;format="camel"$CreatedEventDMLStatement = insert$name;format="Camel"$
      $name;format="camel"$CreatedEventByNaturalKeyDMLStatement = insert$name;format="Camel"$ByNaturalKey
      Done
    }
  }

  private def $name;format="camel"$CreatedEventDML(eventElement: EventStreamElement[$name;format="Camel"$CreatedEvent]): Future[List[BoundStatement]] = {
    Future.successful(
      List(
        $name;format="camel"$CreatedEventDMLStatement.bind(
          eventElement.event.$name;format="camel"$Id,
          eventElement.event.naturalKey,
          eventElement.event.data
       ),
        $name;format="camel"$CreatedEventByNaturalKeyDMLStatement.bind(
          eventElement.event.naturalKey,
          eventElement.event.$name;format="camel"$Id
        )
      )
    )
  }
}