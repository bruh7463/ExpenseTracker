//This module is responsible for keeping track of the total expenses of each user in the database and resets the total on the dashboard page when a new month begins 

package modules

import com.google.inject.AbstractModule
import java.time.{LocalDate, LocalTime, ZoneId}
import java.util.Date
import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import controllers.Expenses
import play.api.inject.ApplicationLifecycle

class SchedulerModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[SchedulerTask]).asEagerSingleton()
  }
}

@Singleton
class SchedulerTask @Inject()(actorSystem: ActorSystem, expenseService: Expenses, lifecycle: ApplicationLifecycle)(implicit ec: ExecutionContext) {

  private def initialDelay: FiniteDuration = {
    val now = LocalDate.now()
    val nextMonth = now.plusMonths(1).withDayOfMonth(1).atStartOfDay()
    val millisUntilNextMonth = nextMonth.atZone(ZoneId.systemDefault()).toInstant.toEpochMilli - System.currentTimeMillis()
    FiniteDuration(millisUntilNextMonth, MILLISECONDS)
  }

  actorSystem.scheduler.scheduleAtFixedRate(
    initialDelay = initialDelay,
    interval = 30.days
  )(() => {
    expenseService.getAllUsernames().foreach { usernames =>
      usernames.foreach { username =>
        expenseService.calculateTotalExpenses(username).foreach { total =>
          // Optionally log the total for each user
          println(s"Total expenses for user $username: $total")
        }
      }
    }
  })

  lifecycle.addStopHook { () =>
    Future.successful(actorSystem.terminate())
  }
}
