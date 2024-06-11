package models

// This is the model that play framework uses when creating and sending a user's details to the database
case class CreateUser(username: String, password: String, securityQuestion: String, securityAnswer: String, income: Double, incomeSource: String)
