package models

import play.api.libs.json.{Json, OFormat}

case class ExpenseWithDate(username: String, item: String, cost: Double, quantity: Int, location: String, date: String)

object ExpenseWithDate {
  implicit val format: OFormat[ExpenseWithDate] = Json.format[ExpenseWithDate]
}