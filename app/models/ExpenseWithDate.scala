package models

import play.api.libs.json.{Json, OFormat}

// This is the model that play framework uses when retrieving expenses from the database
case class ExpenseWithDate(username: String, item: String, cost: Double, quantity: Int, location: String, date: String)

// This object helps to convert the expenses data retrieved from the database into json format for them to be displayed as a graph
object ExpenseWithDate {
  implicit val format: OFormat[ExpenseWithDate] = Json.format[ExpenseWithDate]
}
