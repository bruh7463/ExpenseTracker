package controllers

import javax.inject._
import play.api._
import play.api.mvc._


@Singleton
class ForgetPassword @Inject()(val controllerComponents: ControllerComponents) extends BaseController {


  def forget_password() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.forget_password())
  }

}
