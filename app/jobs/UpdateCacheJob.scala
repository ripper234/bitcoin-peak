package jobs

import akka.actor.Actor
import play.api.Logger
import org.bitcoinpeak.{Config, BlockchainPeakPriceFinder}
import play.api.cache.Cache
import play.api.Play.current

case object Tick

class UpdateCacheJob extends Actor {
  def receive = {
    case Tick => {
      Logger.info("Updating cache")
      val peak = Config.peakService.calcPeak()
      Cache.set("peak", peak)
    }
  }
}
