//This is where everything to do with the income page is done

package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import anorm._
import anorm.SqlParser._
import javax.inject.{Inject, Singleton}
import play.api.db.DBApi
import scala.concurrent.{ExecutionContext, Future}
import java.text.DecimalFormat

case class Users(username: String, income: Double, incomeSource: String)


@Singleton
class Income @Inject()(cc: MessagesControllerComponents)(dbapi: DBApi)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  private val db = dbapi.database("default")

  val userParser: RowParser[Users] = {
    get[String]("username") ~
    get[Double]("income") ~
    get[String]("income_source") map {
      case username ~ income ~ incomeSource =>
        Users(username, income, incomeSource)
    }
  }

  def getUserInfoByUsername(username: String): Future[Option[(Double, String)]] = Future {
    db.withConnection { implicit connection =>
      SQL"SELECT round(income, 2) as income, incomeSource FROM users WHERE username = $username".as((SqlParser.double("income") ~ SqlParser.str("incomeSource")).map {
        case income ~ incomeSource => (income, incomeSource)
      }.singleOpt)
    }
  }

  /*def income() = Action { implicit request: Request[AnyContent] =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username => 
      Ok(views.html.income())
    }.getOrElse(Redirect(routes.Login.login()))
  }*/

  def listIncome: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    request.session.get("username") match {
      case Some(username) =>
          getUserInfoByUsername(username).map {
            case Some((income, incomeSource)) =>
              val formattedIncome = new DecimalFormat("#.00").format(income)
              Ok(views.html.income(formattedIncome.toDouble, incomeSource))
          }
      case None =>
        Future.successful(Redirect(routes.Login.login()).flashing("error" -> "Please log in"))
    }
  }

}
