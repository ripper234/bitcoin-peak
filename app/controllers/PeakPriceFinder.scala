package controllers

import play.api.cache.Cache
import play.api.Play.current
import play.api.Logger

object PeakPriceFinder {
  def getCachedPeak : Int = {
    Cache.getAs[Int]("peak").getOrElse(0)
  }

  def calcPeak() {
    var peak : Option[Int] = Cache.getAs[Int]("peak")
    val peakInt = peak.getOrElse(0)

    Cache.set("peak", peakInt + 1)
  }
}
