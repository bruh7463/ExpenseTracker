package models

// This model helps in retrieving the users info from the database
case class Users(username: String, password: String, securityQuestion: String, securityAnswer: String, income: Double, incomeSource: String)
