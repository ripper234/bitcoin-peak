import jobs._
import akka.actor.Props
import org.bitcoinpeak.DBUpdater
import org.quartz.impl.StdSchedulerFactory
import org.quartz._
import org.quartz.JobBuilder._
import play.api.{GlobalSettings, Logger, Application}
import play.libs.Akka
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Global extends GlobalSettings {
  val quartzScheduler = StdSchedulerFactory.getDefaultScheduler

  override def onStart(app: Application) {
    val monitorActor = Akka.system.actorOf(Props[UpdateCacheJob], name = "updateCacheLoad")
    Akka.system.scheduler.schedule(0 seconds, 60 seconds, monitorActor, Tick)

    // define the job and tie it to our HelloJob class
    val job = newJob(classOf[DBUpdater])
        .withIdentity("job1", "group1")
        .build()

    // Trigger the job to run now, and then repeat every 40 seconds
    val trigger = TriggerBuilder.newTrigger()
          .withIdentity("trigger1", "group1")
          .startNow()
          .withSchedule(CronScheduleBuilder.cronSchedule("0 0 9 1/1 * ? *")) // 9AM every day GMT
          .build()

    // Tell quartz to schedule the job using our trigger
    quartzScheduler.scheduleJob(job, trigger)

    // and start it off
    quartzScheduler.start()
  }

  override def onStop(app: Application) {
    quartzScheduler.shutdown()
    Logger.info("Application shutdown...")
  }
}

