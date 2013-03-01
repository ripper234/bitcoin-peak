package jobs

import akka.actor.Actor
import play.api.Logger
import org.bitcoinpeak.BlockchainPeakPriceFinder

case object Tick

class UpdateCacheJob extends Actor {
  def receive = {
    case Tick => {
      Logger.info("Updating cache")
      BlockchainPeakPriceFinder.calcPeak()
    }
  }
}
