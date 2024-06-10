package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile: slick.jdbc.JdbcProfile = slick.jdbc.SQLiteProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for
  // tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Expenses.schema ++ PlayEvolutions.schema ++ Users.schema

  /** Entity class storing rows of table Expenses
   *  @param username Database column username SqlType(TEXT)
   *  @param itembought Database column itemBought SqlType(TEXT)
   *  @param price Database column price SqlType(REAL)
   *  @param quantity Database column quantity SqlType(INTEGER)
   *  @param shopname Database column shopName SqlType(TEXT)
   *  @param date Database column date SqlType(DATESTAMP) */
  case class ExpensesRow(username: Option[String], itembought: Option[String], price: Option[Double], quantity: Option[Int], shopname: Option[String], date: Option[String])
  /** GetResult implicit for fetching ExpensesRow objects using plain SQL queries */
  implicit def GetResultExpensesRow(implicit e0: GR[Option[String]], e1: GR[Option[Double]], e2: GR[Option[Int]]): GR[ExpensesRow] = GR{
    prs => import prs._
    (ExpensesRow.apply _).tupled((<<?[String], <<?[String], <<?[Double], <<?[Int], <<?[String], <<?[String]))
  }
  /** Table description of table expenses. Objects of this class serve as prototypes for rows in queries. */
  class Expenses(_tableTag: Tag) extends profile.api.Table[ExpensesRow](_tableTag, "expenses") {
    def * = ((username, itembought, price, quantity, shopname, date)).mapTo[ExpensesRow]

    /** Database column username SqlType(TEXT) */
    val username: Rep[Option[String]] = column[Option[String]]("username")
    /** Database column itemBought SqlType(TEXT) */
    val itembought: Rep[Option[String]] = column[Option[String]]("itemBought")
    /** Database column price SqlType(REAL) */
    val price: Rep[Option[Double]] = column[Option[Double]]("price")
    /** Database column quantity SqlType(INTEGER) */
    val quantity: Rep[Option[Int]] = column[Option[Int]]("quantity")
    /** Database column shopName SqlType(TEXT) */
    val shopname: Rep[Option[String]] = column[Option[String]]("shopName")
    /** Database column date SqlType(DATESTAMP) */
    val date: Rep[Option[String]] = column[Option[String]]("date")

    /** Foreign key referencing Users (database name users_FK_1) */
    lazy val usersFk = foreignKey("users_FK_1", username, Users)(r => r.username, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Expenses */
  lazy val Expenses = new TableQuery(tag => new Expenses(tag))

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column id SqlType(INT), PrimaryKey
   *  @param hash Database column hash SqlType(VARCHAR), Length(255,true)
   *  @param appliedAt Database column applied_at SqlType(TIMESTAMP)
   *  @param applyScript Database column apply_script SqlType(TEXT)
   *  @param revertScript Database column revert_script SqlType(TEXT)
   *  @param state Database column state SqlType(VARCHAR), Length(255,true)
   *  @param lastProblem Database column last_problem SqlType(TEXT) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[String], revertScript: Option[String], state: Option[String], lastProblem: Option[String])
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    (PlayEvolutionsRow.apply _).tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends profile.api.Table[PlayEvolutionsRow](_tableTag, "play_evolutions") {
    def * = ((id, hash, appliedAt, applyScript, revertScript, state, lastProblem)).mapTo[PlayEvolutionsRow]
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem)).shaped.<>({r=>import r._; _1.map(_=> (PlayEvolutionsRow.apply _).tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash SqlType(VARCHAR), Length(255,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(255,varying=true))
    /** Database column applied_at SqlType(TIMESTAMP) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("applied_at")
    /** Database column apply_script SqlType(TEXT) */
    val applyScript: Rep[Option[String]] = column[Option[String]]("apply_script")
    /** Database column revert_script SqlType(TEXT) */
    val revertScript: Rep[Option[String]] = column[Option[String]]("revert_script")
    /** Database column state SqlType(VARCHAR), Length(255,true) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(255,varying=true))
    /** Database column last_problem SqlType(TEXT) */
    val lastProblem: Rep[Option[String]] = column[Option[String]]("last_problem")
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))

  /** Entity class storing rows of table Users
   *  @param username Database column username SqlType(VARCHAR), PrimaryKey, Length(45,true)
   *  @param userpassword Database column userpassword SqlType(VARCHAR), Length(45,true)
   *  @param securityquestion Database column securityQuestion SqlType(VARCHAR), Length(100,true)
   *  @param securityanswer Database column securityAnswer SqlType(VARCHAR), Length(100,true)
   *  @param income Database column income SqlType(DECIMAL), Length(10,false)
   *  @param incomesource Database column incomeSource SqlType(VARCHAR), Length(175,true) */
  case class UsersRow(username: Option[String], userpassword: Option[String], securityquestion: Option[String], securityanswer: Option[String], income: Option[Double], incomesource: Option[String])
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Option[String]], e1: GR[Option[Double]]): GR[UsersRow] = GR{
    prs => import prs._
    (UsersRow.apply _).tupled((<<?[String], <<?[String], <<?[String], <<?[String], <<?[Double], <<?[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = ((username, userpassword, securityquestion, securityanswer, income, incomesource)).mapTo[UsersRow]

    /** Database column username SqlType(VARCHAR), PrimaryKey, Length(45,true) */
    val username: Rep[Option[String]] = column[Option[String]]("username", O.PrimaryKey, O.Length(45,varying=true))
    /** Database column userpassword SqlType(VARCHAR), Length(45,true) */
    val userpassword: Rep[Option[String]] = column[Option[String]]("userpassword", O.Length(45,varying=true))
    /** Database column securityQuestion SqlType(VARCHAR), Length(100,true) */
    val securityquestion: Rep[Option[String]] = column[Option[String]]("securityQuestion", O.Length(100,varying=true))
    /** Database column securityAnswer SqlType(VARCHAR), Length(100,true) */
    val securityanswer: Rep[Option[String]] = column[Option[String]]("securityAnswer", O.Length(100,varying=true))
    /** Database column income SqlType(DECIMAL), Length(10,false) */
    val income: Rep[Option[Double]] = column[Option[Double]]("income", O.Length(10,varying=false))
    /** Database column incomeSource SqlType(VARCHAR), Length(175,true) */
    val incomesource: Rep[Option[String]] = column[Option[String]]("incomeSource", O.Length(175,varying=true))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
