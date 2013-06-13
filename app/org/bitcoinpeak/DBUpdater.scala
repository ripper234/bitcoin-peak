package org.bitcoinpeak

import org.quartz.JobExecutionContext

class DBUpdater extends org.quartz.Job {
  def execute(p1: JobExecutionContext) {
    val peak : BigDecimal = BitcoinChartsPeakPriceFinder.calcPeak()
    Peak.addPeak(peak)
  }
}
