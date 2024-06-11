package models

// This model helps in retrieving, creating and sending the users info to and from the database
case class Users(username: String, password: String, securityQuestion: String, securityAnswer: String, income: Double, incomeSource: String)
