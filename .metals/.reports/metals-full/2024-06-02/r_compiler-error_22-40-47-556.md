file:///C:/Users/kay/Desktop/java/expense_tracker/app/controllers/Income.scala
### java.lang.AssertionError: assertion failed: denotation object language invalid in run 3. ValidFor: Period(1..2, run = 4)

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
	dotty.tools.dotc.core.Denotations$SingleDenotation.updateValidity(Denotations.scala:717)
	dotty.tools.dotc.core.Denotations$SingleDenotation.bringForward(Denotations.scala:742)
	dotty.tools.dotc.core.Denotations$SingleDenotation.toNewRun$1(Denotations.scala:799)
	dotty.tools.dotc.core.Denotations$SingleDenotation.current(Denotations.scala:870)
	dotty.tools.dotc.core.Symbols$Symbol.recomputeDenot(Symbols.scala:120)
	dotty.tools.dotc.core.Symbols$Symbol.computeDenot(Symbols.scala:114)
	dotty.tools.dotc.core.Symbols$Symbol.denot(Symbols.scala:107)
	dotty.tools.dotc.core.Symbols$.toDenot(Symbols.scala:494)
	dotty.tools.dotc.typer.Checking.checkLegalImportPath(Checking.scala:938)
	dotty.tools.dotc.typer.Checking.checkLegalImportPath$(Checking.scala:809)
	dotty.tools.dotc.typer.Typer.checkLegalImportPath(Typer.scala:117)
	dotty.tools.dotc.typer.Typer.typedImport(Typer.scala:2789)
	dotty.tools.dotc.typer.Typer.typedUnnamed$1(Typer.scala:3062)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3115)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3187)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3191)
	dotty.tools.dotc.typer.Typer.traverse$1(Typer.scala:3203)
	dotty.tools.dotc.typer.Typer.typedStats(Typer.scala:3259)
	dotty.tools.dotc.typer.Typer.typedPackageDef(Typer.scala:2812)
	dotty.tools.dotc.typer.Typer.typedUnnamed$1(Typer.scala:3083)
	dotty.tools.dotc.typer.Typer.typedUnadapted(Typer.scala:3115)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3187)
	dotty.tools.dotc.typer.Typer.typed(Typer.scala:3191)
	dotty.tools.dotc.typer.Typer.typedExpr(Typer.scala:3303)
	dotty.tools.dotc.typer.TyperPhase.typeCheck$$anonfun$1(TyperPhase.scala:44)
	dotty.tools.dotc.typer.TyperPhase.typeCheck$$anonfun$adapted$1(TyperPhase.scala:50)
	scala.Function0.apply$mcV$sp(Function0.scala:42)
	dotty.tools.dotc.core.Phases$Phase.monitor(Phases.scala:440)
	dotty.tools.dotc.typer.TyperPhase.typeCheck(TyperPhase.scala:50)
	dotty.tools.dotc.typer.TyperPhase.runOn$$anonfun$3(TyperPhase.scala:84)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.immutable.List.foreach(List.scala:333)
	dotty.tools.dotc.typer.TyperPhase.runOn(TyperPhase.scala:84)
	dotty.tools.dotc.Run.runPhases$1$$anonfun$1(Run.scala:246)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
	scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
	scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1323)
	dotty.tools.dotc.Run.runPhases$1(Run.scala:262)
	dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:270)
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

java.lang.AssertionError: assertion failed: denotation object language invalid in run 3. ValidFor: Period(1..2, run = 4)