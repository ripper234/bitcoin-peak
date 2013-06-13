package org.bitcoinpeak

object BitcoinChartsPeakPriceFinder extends PeakPriceFinder{
  def calcPeak() : BigDecimal = {
    // BitcoinCharts only return 20,000 records, which mean the max price
    // is only a max of recent months. We compare with the old peak stored in DB
    val chartsPeak: BigDecimal = findPeakFromBitcoinCharts
    val oldPeak: BigDecimal = Peak.getLastPeak

    chartsPeak.max(oldPeak)
  }

  def findPeakFromBitcoinCharts: BigDecimal = {
    val startTime = 1361836800 // unixtime, Deb 26 2012
    val url = "http://bitcoincharts.com/t/trades.csv"

    val csv = play.libs.WS.url(url).setQueryParameter("start", startTime.toString).
      setQueryParameter("symbol", "mtgoxUSD").get.get.getBody
    val lines = csv.split("\r|\n")
    val peak = lines.map(line => {
      var parts: Array[String] = line.split(",")
      val price = BigDecimal(parts {
        1
      })
      price
    }).max
    peak
  }
}
