import jobs._
import akka.actor.Props
import play.api.{GlobalSettings, Logger, Application}
import play.libs.Akka
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    val monitorActor = Akka.system.actorOf(Props[UpdateCacheJob], name = "updateCacheLoad")
    Akka.system.scheduler.schedule(0 seconds, 2 seconds, monitorActor, Tick)
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }
}
