package org.bitcoinpeak

import play.api.cache.Cache
import play.api.Play.current
import scala.collection.JavaConversions._
import org.codehaus.jackson.JsonNode

object BlockchainPeakPriceFinder extends PeakPriceFinder {
  override def calcPeak() : BigDecimal = {
    val url = "http://blockchain.info/charts/market-price"
    val json = play.libs.WS.url(url).setQueryParameter("timespan", "all").
      setQueryParameter("format", "json").get().get().asJson()

    val values = json.get("values").toList

    val peak = (for (value <- values) yield {
      getValue(value)
    }).max

    var peakPoint = values.find(n => n.get("y").getDecimalValue.equals(peak))
    peak
  }

  private def getValue(value: JsonNode): BigDecimal = {
    val timestamp = value.get("x").asLong()
    if (timestamp == 1307643305 || timestamp == 1307729705) {
      // Account for a bug in blockchain.info data
      // https://bitcointalk.org/index.php?topic=147068.msg1563434#msg1563434
      return BigDecimal(0)
    }

    value.get("y").getDecimalValue
  }
}
