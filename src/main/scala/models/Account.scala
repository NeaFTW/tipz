package models

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoCollection, MongoConnection, MongoCursor}

/**
  * Created by root on 27/12/16.
  */
class Account {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("account")

  /**
    * Get all account into the database
    * @return list of the account
    */
  def findAllAccounts() = {
    val res = mongoDB.find().toList
    res
  }

  /**
    * Getting the account associated to the email
    * @param email
    * @return the list containing the account
    */
  def findByEmail(email : String) = {
    val query = MongoDBObject("email" -> email)
    val res = mongoDB.find(query).toList
    res
  }

  /**
    * Function that insert the information of a new account into the database
    * @param email
    * @param password
    * @param firstname
    * @param lastname
    * @return true or false
    */
  def createUser(email : String, password : String, firstname : String, lastname : String) = {
    /* Getting the initial size of the collection */
    val initNb = mongoDB.count()

    /* Creating the mongoObject of the user */
    val builder = MongoDBObject.newBuilder
    builder += "email" -> email
    builder += "password" -> password
    builder += "firstname" -> firstname
    builder += "lastname" -> lastname
    val user = builder.result

    /* Insert data into the collection */
    mongoDB.insert(user)

    /* Check if the value has been added */
    if (initNb + 1 == mongoDB.count())
      true
    else
      false
  }

  /**
    * Function that close the connection to the database
    */
  def closeConnection() = {
    mongoConn.close()
  }
}
