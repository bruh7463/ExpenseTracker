file:///C:/Users/kay/Desktop/java/expense_tracker/app/controllers/Income.scala
### java.lang.AssertionError: assertion failed: phase parser has already been used once; cannot be reused

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 3.3.3
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala3-library_3\3.3.3\scala3-library_3-3.3.3.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.12\scala-library-2.13.12.jar [exists ]
Options:



action parameters:
uri: file:///C:/Users/kay/Desktop/java/expense_tracker/app/controllers/Income.scala
text:
```scala
package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import anorm._
import anorm.SqlParser._
import javax.inject.{Inject, Singleton}
import play.api.db.DBApi
import scala.concurrent.{ExecutionContext, Future}

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
              Ok(views.html.income(income, incomeSource))
          }
      case None =>
        Future.successful(Redirect(routes.Login.login()).flashing("error" -> "Please log in"))
    }
  }

}

```



#### Error stacktrace:

```
scala.runtime.Scala3RunTime$.assertFailed(Scala3RunTime.scala:8)
	dotty.tools.dotc.core.Phases$Phase.init(Phases.scala:406)
	dotty.tools.dotc.core.Phases$Phase.init(Phases.scala:420)
	dotty.tools.dotc.core.Phases$PhasesBase.usePhases(Phases.scala:168)
	dotty.tools.dotc.core.Phases$PhasesBase.usePhases$(Phases.scala:37)
	dotty.tools.dotc.core.Contexts$ContextBase.usePhases(Contexts.scala:852)
	dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:231)
	dotty.tools.dotc.Run.compileUnits$$anonfun$adapted$1(Run.scala:279)
	dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:71)
	dotty.tools.dotc.Run.compileUnits(Run.scala:279)
	dotty.tools.dotc.Run.compileSources(Run.scala:194)
	dotty.tools.dotc.interactive.InteractiveDriver.run(InteractiveDriver.scala:165)
	scala.meta.internal.pc.MetalsDriver.run(MetalsDriver.scala:45)
	scala.meta.internal.pc.SemanticdbTextDocumentProvider.textDocument(SemanticdbTextDocumentProvider.scala:34)
	scala.meta.internal.pc.ScalaPresentationCompiler.semanticdbTextDocument$$anonfun$1(ScalaPresentationCompiler.scala:217)
```
#### Short summary: 

java.lang.AssertionError: assertion failed: phase parser has already been used once; cannot be reused