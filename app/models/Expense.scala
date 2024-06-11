package models

//This is the model that play framework uses when creating and sending an expense to the database
case class Expense(username: String, item: String, cost: Double, quantity: Int, location: String)
