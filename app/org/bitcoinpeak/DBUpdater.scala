package org.bitcoinpeak

import org.quartz.JobExecutionContext
import play.api.Logger

class DBUpdater extends org.quartz.Job {
  def execute(p1: JobExecutionContext) {
    val peak : BigDecimal = BitcoinChartsPeakPriceFinder.calcPeak()
    Logger.info("Updating database with peak " + peak)

    Peak.addPeak(peak)
  }
}
