package models

case class Users(username: String, password: String, securityQuestion: String, securityAnswer: String, income: Double, incomeSource: String)
