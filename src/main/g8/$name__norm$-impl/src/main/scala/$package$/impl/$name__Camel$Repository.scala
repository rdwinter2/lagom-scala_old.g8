package $package$.impl

import $package$.api._

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

class $name;format="Camel"$Repository(session: CassandraSession)(implicit ec: ExecutionContext) {

  def find$name;format="Camel"$ByNaturalKey(naturalKey: String): Future[Option[$name;format="Camel"$ByNaturalKey]] = {
    val result = cassandraSession.selectOne(
      """
      | SELECT $name;format="snake,upper"$_ID
      | FROM $name;format="snake,upper"$_BY_NATURAL_KEY
      | WHERE NATURAL_KEY = ?
      """
      , naturalKey).map {
      case Some(row) => Option(
        $name;format="Camel"$ByNaturalKey(
          $name;format="camel"$Id = row.getString("$name;format="snake,upper"$_ID")
        )
      )
      case None => Option.empty
    }

    result
  }

  def get$name;format="Camel"$($name;format="Camel"$Id: String) = {
    cassandraSession.selectOne(
      """
      | SELECT NATURAL_KEY, DATA
      | FROM $name;format="snake,upper"$_BY_NATURAL_KEY
      | WHERE $name;format="snake,upper"$_ID = ?
      """, $name;format="Camel"$Id).map {
      case None => throw new IllegalStateException("No $name$ found for surrogate key " + $name;format="Camel"$Id)
      case Some(row) =>
        s"The $name$ for surrogate key: \$$name;format="Camel"$Id is natural key: ${row.getString("NATURAL_KEY")} and data: ${row.getString("DATA")}\n"
    }
  }
}