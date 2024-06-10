package controllers

import javax.inject._
import play.api._
import play.api.mvc._


@Singleton
class Report @Inject()(val controllerComponents: ControllerComponents) extends BaseController {


  def report() = Action { implicit request: Request[AnyContent] =>
    Ok("Report")
  }

}
