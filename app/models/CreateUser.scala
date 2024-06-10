package models

case class CreateUser(username: String, password: String, securityQuestion: String, securityAnswer: String, income: Double, incomeSource: String)
