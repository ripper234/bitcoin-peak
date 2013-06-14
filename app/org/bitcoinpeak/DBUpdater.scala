package org.bitcoinpeak

import org.quartz.JobExecutionContext
import play.api.Logger

class DBUpdater extends org.quartz.Job {
  def execute(p1: JobExecutionContext) {
    Logger.info("Updating database")
    val peak : BigDecimal = BitcoinChartsPeakPriceFinder.calcPeak()
    Peak.addPeak(peak)
  }
}
