package models

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoCollection, MongoConnection, MongoCursor}
import com.sun.javafx.cursor.CursorType

/**
  * Created by root on 27/12/16.
  */
class Account {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("account")

  def findAllAccount() = {
    val res = mongoDB.find().toArray
    res
  }

  def findByEmail(email : String) = {
    val query = MongoDBObject("email" -> email)
    val res = mongoDB.find(query).toArray
    res
  }

  def createUser(email : String, password : String, firstname : String, lastname : String) = {
    /* Getting the initial size of the collection */
    val initNb = mongoDB.find().size

    /* Creating the mongoObject of the user */
    val user = MongoDBObject(
      "email" -> email,
      "password" -> password,
      "firstname" -> firstname,
      "lastname" -> lastname
    )

    /* Insert data into the collection */
    mongoDB.insert(user)

    /* Check if the value has been added */
    if (initNb + 1 == mongoDB.count())
      true
    else
      false
  }

  def closeConnection() = {
    mongoConn.close()
  }
}
