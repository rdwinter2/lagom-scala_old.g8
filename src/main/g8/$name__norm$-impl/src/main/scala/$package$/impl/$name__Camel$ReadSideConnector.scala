package $package$.impl

import java.util.UUID

import $package$.api.$name;format="Camel"$Response
import $organization$.common.utils.{ErrorResponse, ErrorResponses => ER}
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class $name;format="Camel"$ReadSideConnector(session: CassandraSession)(implicit ec: ExecutionContext) {

  def get$name;format="Camel"$s: Future[Iterable[$name;format="Camel"$Response]] = {
    session.selectAll(
      """
        |SELECT * FROM $name;format="camel"$s
      """.stripMargin)
      .map(_
        .map(row =>
          Try(
            $name;format="Camel"$Response(
              UUID.fromString(row.getString("id")),
              row.getString("$name;format="camel"$name"),
              row.getString("email"),
              row.getString("status") == $name;format="Camel"$Status.VERIFIED.toString)).toOption)
        .filter {
          case Some(_) => true
          case _ => false }
        .map(_.get))
  }

  def get$name;format="Camel"$IdFromAccessToken(access_token: UUID): Future[Either[ErrorResponse, UUID]] = {
    session
      .selectAll(
          s"""
            |SELECT $name;format="camel"$id FROM sessions WHERE access_token = '$access_token'
          """.stripMargin)
      .map(_.headOption.toRight(ER.NotFound("Access token missing")).map(row => UUID.fromString(row.getString("$name;format="camel"$id"))))
  }

  def get$name;format="Camel"$IdFromRefreshToken(refresh_token: UUID): Future[Either[ErrorResponse, UUID]] = {
    session
      .selectAll(
        s"""
           |SELECT $name;format="camel"$id FROM sessions WHERE refresh_token = '$refresh_token' ALLOW FILTERING
          """.stripMargin)
      .map(_.headOption.toRight(ER.NotFound("Refresh token not found")).map(row => UUID.fromString(row.getString("$name;format="camel"$id"))))
  }

  def get$name;format="Camel"$IdFrom$name;format="Camel"$name($name;format="camel"$name: String): Future[Either[ErrorResponse, UUID]] = {
    session
      .selectAll(
        s"""
           |SELECT id FROM $name;format="camel"$s WHERE $name;format="camel"$name = '$$name;format="camel"$name' ALLOW FILTERING
          """.stripMargin)
      .map(_.headOption.toRight(ER.NotFound("$name;format="Camel"$name not found")).map(row => UUID.fromString(row.getString("id"))))
  }

  def get$name;format="Camel"$(id: UUID): Future[Either[ErrorResponse, $name;format="Camel"$Response]] = {
    session
      .selectAll(
        s"""
           |SELECT * FROM $name;format="camel"$s WHERE id = '${id.toString}'
          """.stripMargin)
      .map(_.headOption
        .toRight(ER.NotFound("$name;format="Camel"$ not found with id"))
        .fold(
          e => Left(e),
          row => Try(
            $name;format="Camel"$Response(
              UUID.fromString(row.getString("id")),
              row.getString("$name;format="camel"$name"),
              row.getString("email"),
              row.getString("status") == $name;format="Camel"$Status.VERIFIED.toString))
            .toOption
            .toRight(ER.InternalServerError("Data corrupted"))))
  }
}
