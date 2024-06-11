//This model was a test model and does not help with accessing the database

package models

import collection.mutable

object Expenses {
	private val expenses = mutable.Map[String, List[String]]("user" -> List("Bread", "10.00", "2", "Shoprite", "24/05/2024"))

	def addExpense(username: String, itemBought: String, price: String, quantity: String, shopName: String, date: String) = ???

	def getInfo(username: String): Seq[String] = {
		expenses.get(username).getOrElse(Nil)
	}
}

