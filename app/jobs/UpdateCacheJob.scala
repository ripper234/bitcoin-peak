package jobs

import akka.actor.Actor
import play.api.Logger
import org.bitcoinpeak.{Config, BlockchainPeakPriceFinder}
import play.api.cache.Cache
import play.api.Play.current
import org.joda.time.DateTime

case object Tick

class UpdateCacheJob extends Actor {
  def receive = {
    case Tick => {
      val peak = Config.peakService.calcPeak()
      Logger.info(new DateTime() + " - Updating cache with new peak " + peak)
      Cache.set("peak", peak)
    }
  }
}
