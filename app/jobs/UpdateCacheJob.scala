package jobs

import akka.actor.Actor
import play.api.Logger
import org.bitcoinpeak.BlockchainPeakPriceFinder
import play.api.cache.Cache

case object Tick

class UpdateCacheJob extends Actor {
  def receive = {
    case Tick => {
      Logger.info("Updating cache")
      val peak = BlockchainPeakPriceFinder.calcPeak()
      Cache.set("peak", peak)
    }
  }
}
