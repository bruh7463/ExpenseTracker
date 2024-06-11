//This is where everything to do with the login page is done

package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import anorm._
import anorm.SqlParser._
import javax.inject.{Inject, Singleton}
import play.api.db.DBApi
import scala.concurrent.{ExecutionContext, Future}
import models.UsersDB

@Singleton
class Login @Inject()(cc: MessagesControllerComponents)(dbapi: DBApi)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  private val db = dbapi.database("default")

  val userParser: RowParser[UsersDB] = {
    get[String]("username") ~
    get[String]("userpassword") map {
      case username ~ password => UsersDB(username, password)
    }
  }

  def validate(username: String, password: String): Future[Boolean] = Future {
    db.withConnection { implicit connection =>
      SQL"SELECT * FROM users WHERE username = $username AND userpassword = $password".as(userParser.singleOpt).isDefined
    }
  }


  def login() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login())
  }

  /*def validateUser() = Action { implicit request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args => 
      val username = args("username").head
      val password = args("password").head
      if (Users.validateUser(username, password)) {
        Redirect(routes.Dashboard.dashboard())
      } else {
        Redirect(routes.Login.login())
      }
      //Ok(s"Okay, $username, $password")

    }.getOrElse(Ok("Error!!!!!!"))
  }*/

  def validateUser: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val formData = request.body.asFormUrlEncoded
    formData match {
      case Some(data) =>
        val usernameOpt = data.get("username").flatMap(_.headOption)
        val passwordOpt = data.get("password").flatMap(_.headOption)
        
        (usernameOpt, passwordOpt) match {
          case (Some(username), Some(password)) =>
            validate(username, password).map { isValid =>
              if (isValid) {
                Redirect(routes.Dashboard.dashboard())
                  .flashing("success" -> "Login successful")
                  .withSession("username" -> username)
              } else {
                Redirect(routes.Login.login())
                  .flashing("error" -> "Invalid username or password")
              }
            }
          case _ =>
            Future.successful(BadRequest(views.html.login()).flashing("error" -> "Missing username or password"))
        }
      case None =>
        Future.successful(BadRequest(views.html.login()).flashing("error" -> "Form data error"))
    }
  }

  def logout: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Redirect(routes.Login.login())
      .flashing("success" -> "You've been logged out")
      .withNewSession
  }

}
