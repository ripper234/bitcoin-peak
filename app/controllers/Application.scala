package controllers

import play.api._
import cache.Cache
import play.api.mvc._
import org.bitcoinpeak.{DBUpdater, BitcoinChartsPeakPriceFinder, Config}
import play.api.Play.current
import org.quartz.impl.JobExecutionContextImpl

object Application extends Controller {

  def index = Action {
    val peak = Cache.getAs[BigDecimal]("peak").getOrElse(BigDecimal(0))
    Ok(views.html.index(peak))
  }

  def calc = Action {
    val peak : BigDecimal = Config.peakService.calcPeak()
    Ok(peak.toString())
  }

  def calcMtgox = Action {
    val peak : BigDecimal = BitcoinChartsPeakPriceFinder.calcPeak()
    Ok(peak.toString() + ", calculated by " + BitcoinChartsPeakPriceFinder.getClass)
  }

  def updateDB = Action {
    new DBUpdater().execute(null)
    Ok("Updated peak")
  }
}