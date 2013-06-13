package org.bitcoinpeak

import java.math.BigDecimal
import anorm._
import AnormExtensions._
import play.api.db._
import play.api.Play.current
import anorm.SqlParser._
import org.joda.time.DateTime

case class Peak(
  time: DateTime,
  price: BigDecimal
)

object Peak {
  val allFieldsParser = {
    get[DateTime]("peaks.time") ~
    get[BigDecimal]("peaks.price") map {
      case time ~ price =>
        Peak(time, price)
    }
  }

  def getLastPeak: BigDecimal = {
    val peak = DB.withConnection { implicit conn =>


      SQL("SELECT peak from Peaks ORDER BY time desc limit 1")
        .as(Peak.allFieldsParser *)
    }
    if (peak.isEmpty) {
      new BigDecimal(266) // April 2013
    } else {
      peak.head.price
    }
  }

  def addPeak(peak : BigDecimal) {
    DB.withConnection { implicit conn =>
      SQL("INSERT INTO Peaks(peak) VALUES ({peak})")
        .on("peak" -> peak)
        .executeInsert()

    }
  }
}
