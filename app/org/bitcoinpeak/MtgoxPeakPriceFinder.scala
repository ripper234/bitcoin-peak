package org.bitcoinpeak

object MtgoxPeakPriceFinder extends PeakPriceFinder{
  def calcPeak() : BigDecimal = {
    val startTime = 1361836800 // unixtime, Deb 26 2012
    val url = "http://bitcoincharts.com/t/trades.csv"

    val csv = play.libs.WS.url(url).setQueryParameter("start", startTime.toString).
      setQueryParameter("symbol", "mtgoxUSD").get.get.getBody
    val lines = csv.split("\r|\n")
    val peak = lines.map(line => {
      var parts: Array[String] = line.split("\\s")
      val price = BigDecimal(parts{2})
      price
    }).max
    peak
  }
}
