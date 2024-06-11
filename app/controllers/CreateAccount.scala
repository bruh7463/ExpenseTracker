//This is where everything to do with the create account page is done

package controllers

import javax.inject._
import play.api.mvc._
import anorm._
import anorm.SqlParser._
import javax.inject.{Inject, Singleton}
import play.api.db.DBApi
import scala.concurrent.{ExecutionContext, Future}


case class User(username: String, password: String, securityQuestion: String, securityAnswer: String, income: Double, incomeSource: String)

@Singleton
class CreateAccount @Inject()(cc: MessagesControllerComponents)(dbapi: DBApi)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  private val db = dbapi.database("default")

  // This method connects with the database and uses a sql query to insert user info into the database
  def createUser(user: User): Future[Option[Long]] = Future {
    db.withConnection { implicit connection =>
      SQL"""
        INSERT INTO users (username, userpassword, securityQuestion, securityAnswer, income, incomeSource)
        VALUES (${user.username}, ${user.password}, ${user.securityQuestion}, ${user.securityAnswer}, ${user.income}, ${user.incomeSource})
      """.executeInsert()
    }
  }


  //This method displays the create_account page on the browser
  def create_account() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.create_account())
  }

  /*def createUser() = Action { implicit request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args => 
      val username = args("username").head
      val password = args("password").head
      val securityQuestion = args("securityQuestion").head
      val securityAnswer = args("securityAnswer").head
      val income = args("income").head
      val incomeSource = args("incomeSource").head
      //Ok(s"Okay, $username, $password, $securityQuestion, $securityAnswer, $income, $incomeSource")
      if (Users.createUser(username, password, securityQuestion, securityAnswer, income, incomeSource)) {
        Redirect(routes.Login.login())
      } else {
        Redirect(routes.CreateAccount.create_account())
      }

    }.getOrElse(Redirect(routes.CreateAccount.create_account()))
  }*/

  //This method maps the data entered from the create_account form and sends it to the createUser method
  def createUser: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val formData = request.body.asFormUrlEncoded
    formData match {
      case Some(data) =>
        val usernameOpt = data.get("username").flatMap(_.headOption)
        val passwordOpt = data.get("password").flatMap(_.headOption)
        val securityQuestionOpt = data.get("securityQuestion").flatMap(_.headOption)
        val securityAnswerOpt = data.get("securityAnswer").flatMap(_.headOption)
        val incomeOpt = data.get("income").flatMap(_.headOption).map(_.toDouble)
        val incomeSourceOpt = data.get("incomeSource").flatMap(_.headOption)

        (usernameOpt, passwordOpt, securityQuestionOpt, securityAnswerOpt, incomeOpt, incomeSourceOpt) match {
          case (Some(username), Some(password), Some(securityQuestion), Some(securityAnswer), Some(income), Some(incomeSource)) =>
            val user = User(username, password, securityQuestion, securityAnswer, income, incomeSource)
            createUser(user).map { _ =>
              Redirect(routes.Login.login()).flashing("success" -> "Account created successfully")
            }
          case _ =>
            Future.successful(BadRequest(views.html.create_account()).flashing("error" -> "All fields are required"))
        }
      case None =>
        Future.successful(BadRequest(views.html.create_account()).flashing("error" -> "Form data error"))
    }
  }
}
