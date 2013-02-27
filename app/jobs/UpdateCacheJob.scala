package jobs

import akka.actor.Actor
import controllers.PeakPriceFinder
import play.api.Logger

case object Tick

class UpdateCacheJob extends Actor {
  def receive = {
    case Tick => {
      Logger.info("Updating cache")
      PeakPriceFinder.calcPeak()
    }
  }
}
