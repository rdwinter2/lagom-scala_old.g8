package $package$.impl.domain

import $package$.api

import akka.Done
import akka.persistence.cassandra.ListenableFutureConverter
import akka.stream.Materializer
import collection.JavaConverters._
import com.datastax.driver.core._
import com.example.auction.common.utils
import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession}
import com.lightbend.lagom.scaladsl.persistence.ReadSideProcessor
import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

private[impl] class $name;format="Camel"$Repository(session: CassandraSession)(implicit ec: ExecutionContext, mat: Materializer) {

  def get$name;format="Camel"$sForUser(creatorId: UUID, status: $name;format="Camel"$Status.Status, page: Option[String], fetchSize: Int): Future[utils.PagingState[api.$name;format="Camel"$Summary]] = {
    for {
      count <- count$name;format="Camel"$sByCreatorInStatus(creatorId, status)
      $name;format="camel"$sWithNextPage <- select$name;format="Camel"$sByCreatorInStatusWithPaging(creatorId, status, page, fetchSize)
    } yield {
      val $name;format="camel"$s = $name;format="camel"$sWithNextPage._1
      val nextPage = $name;format="camel"$sWithNextPage._2.getOrElse("")
      utils.PagingState($name;format="camel"$s, nextPage, count)
    }
  }

  private def count$name;format="Camel"$sByCreatorInStatus(creatorId: UUID, status: $name;format="Camel"$Status.Status) = {
    session.selectOne("""
      SELECT COUNT(*) FROM $name;format="camel"$SummaryByCreatorAndStatus
      WHERE creatorId = ? AND status = ?
      ORDER BY status ASC, $name;format="camel"$Id DESC
    """, // ORDER BY status is required due to https://issues.apache.org/jira/browse/CASSANDRA-10271
      creatorId, status.toString).map {
      case Some(row) => row.getLong("count").toInt
      case None => 0
    }
  }

  private def select$name;format="Camel"$sByCreatorInStatus(creatorId: UUID, status: $name;format="Camel"$Status.Status, offset: Int, limit: Int) = {
    session.selectAll("""
      SELECT * FROM $name;format="camel"$SummaryByCreatorAndStatus
      WHERE creatorId = ? AND status = ?
      ORDER BY status ASC, $name;format="camel"$Id DESC
      LIMIT ?
    """, creatorId, status.toString, Integer.valueOf(limit)).map { rows =>
      rows.drop(offset)
       .map(convert$name;format="Camel"$Summary)
    }
  }

  /**
    * Motivation: https://discuss.lightbend.com/t/how-to-specify-pagination-for-select-query-read-side/870
    */
  private def select$name;format="Camel"$sByCreatorInStatusWithPaging(creatorId: UUID,
                                                     status: $name;format="Camel"$Status.Status,
                                                     page: Option[String],
                                                     fetchSize: Int): Future[(Seq[api.$name;format="Camel"$Summary], Option[String])] = {
    val statement = new SimpleStatement(
      """
          SELECT * FROM $name;format="camel"$SummaryByCreatorAndStatus
          WHERE creatorId = ? AND status = ?
          ORDER BY status ASC, $name;format="camel"$Id DESC
          """, creatorId, status.toString)

    statement.setFetchSize(fetchSize)

    session.underlying().flatMap(underlyingSession => {

      page.map(pagingStateStr => statement.setPagingState(PagingState.fromString(pagingStateStr)))

      underlyingSession.executeAsync(statement).asScala map (resultSet => {
        val newPagingState = resultSet.getExecutionInfo.getPagingState

        /**
          * @note Check against null due to Java code in `getPagingState` function.
          * @note The `getPagingState` function can return null if there is no next page for this reason nextPage is an
          * Option[String].
          */
        val nextPage: Option[String] = if (newPagingState != null) Some(newPagingState.toString) else None

        val iterator = resultSet.iterator().asScala
        iterator.take(fetchSize).map(convert$name;format="Camel"$Summary).toSeq -> nextPage
      })
    })
  }

  private def convert$name;format="Camel"$Summary($name;format="camel"$: Row): api.$name;format="Camel"$Summary = {
    api.$name;format="Camel"$Summary(
      $name;format="camel"$.getUUID("$name;format="camel"$Id"),
      $name;format="camel"$.getString("title"),
      $name;format="camel"$.getString("currencyId"),
      $name;format="camel"$.getInt("reservePrice"),
      api.$name;format="Camel"$Status.withName($name;format="camel"$.getString("status"))
    )
  }
}

private[impl] class $name;format="Camel"$EventProcessor(session: CassandraSession, readSide: CassandraReadSide)(implicit ec: ExecutionContext)
  extends ReadSideProcessor[$name;format="Camel"$Event] {
  private var insert$name;format="Camel"$CreatorStatement: PreparedStatement = null
  private var insert$name;format="Camel"$SummaryByCreatorStatement: PreparedStatement = null
  private var update$name;format="Camel"$SummaryStatusStatement: PreparedStatement = null

  def buildHandler = {
    readSide.builder[$name;format="Camel"$Event]("$name;format="camel"$EventOffset")
      .setGlobalPrepare(createTables)
      .setPrepare(_ => prepareStatements())
      .setEventHandler[$name;format="Camel"$Created](e => insert$name;format="Camel"$(e.event.$name;format="camel"$))
      .setEventHandler[AuctionStarted](e => update$name;format="Camel"$SummaryStatus(e.entityId, api.$name;format="Camel"$Status.Auction))
      .setEventHandler[AuctionFinished](e => update$name;format="Camel"$SummaryStatus(e.entityId, api.$name;format="Camel"$Status.Completed))
      .build
  }

  def aggregateTags = $name;format="Camel"$Event.Tag.allTags

  private def createTables() = {
    for {
      _ <- session.executeCreateTable("""
        CREATE TABLE IF NOT EXISTS $name;format="camel"$Creator (
          $name;format="camel"$Id timeuuid PRIMARY KEY,
          creatorId UUID
        )
      """)
      _ <- session.executeCreateTable("""
        CREATE TABLE IF NOT EXISTS $name;format="camel"$SummaryByCreator (
          creatorId UUID,
          $name;format="camel"$Id timeuuid,
          title text,
          currencyId text,
          reservePrice int,
          status text,
          PRIMARY KEY (creatorId, $name;format="camel"$Id)
        ) WITH CLUSTERING ORDER BY ($name;format="camel"$Id DESC)
      """)
      _ <- session.executeCreateTable("""
        CREATE MATERIALIZED VIEW IF NOT EXISTS $name;format="camel"$SummaryByCreatorAndStatus AS
          SELECT * FROM $name;format="camel"$SummaryByCreator
          WHERE status IS NOT NULL AND $name;format="camel"$Id IS NOT NULL
          PRIMARY KEY (creatorId, status, $name;format="camel"$Id)
          WITH CLUSTERING ORDER BY (status ASC, $name;format="camel"$Id DESC)
      """)
    } yield Done
  }

  private def prepareStatements() = {
    for {
      insert$name;format="Camel"$Creator <- session.prepare("""
        INSERT INTO $name;format="camel"$Creator($name;format="camel"$Id, creatorId) VALUES (?, ?)
      """)
      insert$name;format="Camel"$Summary <- session.prepare("""
        INSERT INTO $name;format="camel"$SummaryByCreator(
          creatorId,
          $name;format="camel"$Id,
          title,
          currencyId,
          reservePrice,
          status
        ) VALUES (?, ?, ?, ?, ?, ?)
      """)
      update$name;format="Camel"$Status <- session.prepare("""
        UPDATE $name;format="camel"$SummaryByCreator SET status = ? WHERE creatorId = ? AND $name;format="camel"$Id = ?
      """)
    } yield {
      insert$name;format="Camel"$CreatorStatement = insert$name;format="Camel"$Creator
      insert$name;format="Camel"$SummaryByCreatorStatement = insert$name;format="Camel"$Summary
      update$name;format="Camel"$SummaryStatusStatement = update$name;format="Camel"$Status
      Done
    }
  }

  private def insert$name;format="Camel"$($name;format="camel"$: $name;format="Camel"$) = {
    Future.successful(List(
      insert$name;format="Camel"$Creator($name;format="camel"$),
      insert$name;format="Camel"$SummaryByCreator($name;format="camel"$)
    ))
  }

  private def insert$name;format="Camel"$Creator($name;format="camel"$: $name;format="Camel"$) = {
    insert$name;format="Camel"$CreatorStatement.bind($name;format="camel"$.id, $name;format="camel"$.creator)
  }

  private def insert$name;format="Camel"$SummaryByCreator($name;format="camel"$: $name;format="Camel"$) = {
    insert$name;format="Camel"$SummaryByCreatorStatement.bind(
      $name;format="camel"$.creator,
      $name;format="camel"$.id,
      $name;format="camel"$.title,
      $name;format="camel"$.currencyId,
      Integer.valueOf($name;format="camel"$.reservePrice),
      $name;format="camel"$.status.toString
    )
  }

  private def update$name;format="Camel"$SummaryStatus($name;format="camel"$Id: String, status: api.$name;format="Camel"$Status.Status) = {
    val $name;format="camel"$Uuid = UUID.fromString($name;format="camel"$Id)
    select$name;format="Camel"$Creator($name;format="camel"$Uuid).map {
      case None => throw new IllegalStateException("No $name;format="camel"$Creator found for $name;format="camel"$Id " + $name;format="camel"$Id)
      case Some(row) =>
        val creatorId = row.getUUID("creatorId")
        List(update$name;format="Camel"$SummaryStatusStatement.bind(status.toString, creatorId, $name;format="camel"$Uuid))
    }
  }

  private def select$name;format="Camel"$Creator($name;format="camel"$Id: UUID) = {
    session.selectOne("SELECT * FROM $name;format="camel"$Creator WHERE $name;format="camel"$Id = ?", $name;format="camel"$Id)
  }
}
