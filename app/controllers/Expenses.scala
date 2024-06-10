package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import anorm._
import anorm.SqlParser._
import play.api.db.DBApi
import java.time.{LocalDate, YearMonth}
import scala.concurrent.{ExecutionContext, Future}

import models.Expenses
import models.ExpenseWithDate

case class Expense(username: String, item: String, cost: Double, quantity: Int, location: String)


@Singleton
class Expenses @Inject()(cc: MessagesControllerComponents)(dbapi: DBApi)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  private val db = dbapi.database("default")

  val expenseParser: RowParser[Expense] = {
    get[String]("username") ~
    get[String]("item") ~
    get[Double]("cost") ~
    get[Int]("quantity") ~
    get[String]("location") map {
      case username ~ item ~ cost ~ quantity ~ location =>
        Expense(username, item, cost, quantity, location)
    }
  }

  val expenseWithDateParser: RowParser[ExpenseWithDate] = {
    get[String]("username") ~
    get[String]("itemBought") ~
    get[Double]("price") ~
    get[Int]("quantity") ~
    get[String]("shopName") ~
    get[String]("date") map {
      case username ~ item ~ cost ~ quantity ~ location ~ date =>
        ExpenseWithDate(username, item, cost, quantity, location, date)
    }
  }

  def findExpensesByUsername(username: String): Future[List[ExpenseWithDate]] = Future {
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM expenses WHERE username = $username".as(expenseWithDateParser.*)
    }
  }

  def getAllUsernames(): Future[List[String]] = Future {
    db.withConnection { implicit connection =>
      SQL"SELECT username FROM users".as(SqlParser.str("username").*)
    }
  }

  def calculateTotalExpenses(username: String): Future[Double] = Future {
    val currentMonth = YearMonth.now()
    db.withConnection { implicit connection =>
      SQL"""
        SELECT SUM(price * quantity) 
        FROM expenses 
        WHERE username = $username 
        AND strftime('%Y-%m', date) = ${currentMonth.toString}
      """.as(SqlParser.scalar[Double].singleOpt).getOrElse(0.0)
    }
  }

  /*def resetMonthlyTotal(): Unit = {
    val nextMonth = YearMonth.now().plusMonths(1)
    val resetDate = LocalDate.of(nextMonth.getYear, nextMonth.getMonthValue, 1)
  }*/

  def createExpense(expense: Expense): Future[Option[Long]] = Future {
    db.withConnection { implicit connection =>
      SQL"""
        INSERT INTO expenses (username, itemBought, price, quantity, shopName)
        VALUES (${expense.username}, ${expense.item}, ${expense.cost}, ${expense.quantity}, ${expense.location})
      """.executeInsert()
    }
  }

  def addExpense: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val formData = request.body.asFormUrlEncoded
    formData match {
      case Some(data) =>
        val usernameOpt = request.session.get("username")
        val itemOpt = data.get("item").flatMap(_.headOption)
        val costOpt = data.get("cost").flatMap(_.headOption).map(_.toDouble)
        val quantityOpt = data.get("quantity").flatMap(_.headOption).map(_.toInt)
        val locationOpt = data.get("location").flatMap(_.headOption)

        (usernameOpt, itemOpt, costOpt, quantityOpt, locationOpt) match {
            case (Some(username), Some(item), Some(cost), Some(quantity), Some(location)) =>
              val expense = Expense(username, item, cost, quantity, location)
              createExpense(expense).map { _ =>
                Redirect(routes.Dashboard.dashboard()).flashing("success" -> "Expense added successfully")
              }
            case _ =>
              Future.successful(Redirect(routes.Dashboard.dashboard()).flashing("error" -> "All fields are required"))
        }
      case None =>
          Future.successful(Redirect(routes.Dashboard.dashboard()).flashing("error" -> "Form data error"))
    }
  }

  /*def expenses() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.expenses())
  }*/

  /*def expenseList() = Action { implicit request: Request[AnyContent] =>
    val username = "user"
    val expenses = Expenses.getInfo(username)
    val usernameOption = request.session.get("username")
    usernameOption.map { username => 
      Ok(views.html.expenses(expenses))
    }.getOrElse(Redirect(routes.Login.login()))
  }*/

  def listExpenses: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    request.session.get("username") match {
      case Some(username) =>
          findExpensesByUsername(username).map { expenses =>
            Ok(views.html.expenses(expenses))
          }
      case None =>
        Future.successful(Redirect(routes.Login.login()).flashing("error" -> "Please log in"))
    }
  }

}
