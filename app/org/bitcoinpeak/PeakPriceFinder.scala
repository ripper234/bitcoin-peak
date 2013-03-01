package org.bitcoinpeak

abstract class PeakPriceFinder {
  def calcPeak() : BigDecimal
}