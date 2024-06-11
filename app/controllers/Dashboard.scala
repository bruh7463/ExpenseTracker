//This is where everything to do with the dashboard page is done

package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.lang.ProcessBuilder.Redirect
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.Json


@Singleton
class Dashboard @Inject()(cc: MessagesControllerComponents, calExpense: Expenses)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  //This method displays the dashboard page with the users' expense data from the database
  def dashboard: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    request.session.get("username") match {
      case Some(username) =>
        val expensesFuture = calExpense.findExpensesByUsername(username)
        val totalFuture = calExpense.calculateTotalExpenses(username)
        
        // Combine the futures using for-comprehension
        val combinedFuture = for {
          expenses <- expensesFuture
          total <- totalFuture
        } yield (total, expenses)
        
        combinedFuture.map { case (total, expenses) =>
          Ok(views.html.dashboard(total, expenses))
        }.recover {
          case ex: Exception =>
            InternalServerError("Error calculating total expenses")
        }

      case None =>
        Future.successful(Redirect(routes.Login.login()).flashing("error" -> "User not found"))
    }
  }
}
