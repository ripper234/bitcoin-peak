package org.bitcoinpeak

import org.joda.time.DateTime
import anorm._
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}



/**
 * http://stackoverflow.com/questions/11388301/joda-datetime-field-on-play-framework-2-0s-anorm
 */
object AnormExtensions {
  val dateFormatGeneration: DateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmssSS")

  implicit def rowToDateTime: Column[DateTime] = Column.nonNull {
    (value, meta) =>
      value match {
        case ts: java.sql.Timestamp => Right(new DateTime(ts.getTime))
        case d: java.sql.Date => Right(new DateTime(d.getTime))
        case str: java.lang.String => Right(dateFormatGeneration.parseDateTime(str))
        case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass))
      }
  }

  implicit val dateTimeToStatement = new ToStatement[DateTime] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: DateTime) {
      s.setTimestamp(index, new java.sql.Timestamp(aValue.withMillisOfSecond(0).getMillis))
    }
  }

}