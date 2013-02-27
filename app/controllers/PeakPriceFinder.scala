package controllers

import play.api.cache.Cache
import play.api.Play.current
import play.api.Logger
import scala.collection.JavaConversions._
import java.math.BigDecimal
import org.codehaus.jackson.JsonNode

object PeakPriceFinder {
  def getCachedPeak : BigDecimal = {
    Cache.getAs[BigDecimal]("peak").getOrElse(new BigDecimal(0))
  }

  def calcPeak() {
    // val url = "http://blockchain.info/charts/market-price?timespan=all&format=json"
    val url = "http://blockchain.info/charts/market-price"
    var json = play.libs.WS.url(url).setQueryParameter("timespan", "all").
      setQueryParameter("format", "json").get().get().asJson()

    val values = json.get("values").toList

    var peak = (for (value <- values) yield {
      getValue(value)
    }).max

    var peakPoint = values.find(n => n.get("y").getDecimalValue.equals(peak))

    Cache.set("peak", peak)
  }

  def getValue(value: JsonNode): BigDecimal = {
    val timestamp = value.get("x").asLong()
    if (timestamp == 1307643305 || timestamp == 1307729705) {
      // Account for a bug in blockchain.info data
      // https://bitcointalk.org/index.php?topic=147068.msg1563434#msg1563434
      return new BigDecimal(0)
    }

    value.get("y").getDecimalValue
  }
}
