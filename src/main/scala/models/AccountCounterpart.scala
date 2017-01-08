package models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject

/**
  * Created by root on 27/12/16.
  */
class AccountCounterpart {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("accountCounterpart")

  /**
    * Get the list of all account and counterparts form AccountCounterpart table
    * @return List of the data of the database
    */
  def findAll() ={
    val res = mongoDB.find().toList
    res
  }

  /**
    * Get the list of all account and counterpart associated to a project Id
    * @param accountEmail
    * @return
    */
  def findAllCounterpartBoughtByAccount(accountEmail : String) = {
    val query = MongoDBObject(
      "accountEmail" -> accountEmail
    )
    val res = mongoDB.find(query).toList
    res
  }

  /**
    * Get the list of all account and counterpart associated to a counterpart ID
    * @param counterpartId
    * @return list of Account and counterpart
    */
  def findAllAccountBoughtByCounterpart(counterpartId : Int) = {
    val query = MongoDBObject(
      "counterpartId" -> counterpartId
    )
    val res = mongoDB.find(query).toList
    res
  }

  /**
    * Check if a use have participate to a counterpart
    * @param counterpartId
    * @param accountEmail
    * @return true or false
    */
  def isAlreadyBought(counterpartId : Int, accountEmail : String) = {
    val query = MongoDBObject(
      "accountEmail" -> accountEmail,
      "counterpartId" -> counterpartId
    )
    val res = mongoDB.find(query).toList
    if (!res.isEmpty)
      true
    else
      false
  }

  /**
    * Insert the counterpart into the database
    * @param accountEmail
    * @param counterpartId
    * @return true or false
    */
  def insertAccountCounterpart(accountEmail : String, counterpartId : Int) = {
    /* Getting current number of data into the collection */
    val initNb = mongoDB.count()

    /* Creating query */
    val query = MongoDBObject(
      "accountEmail" -> accountEmail,
      "counterpartId" -> counterpartId
    )

    /* Insert Data into database */
    val res = mongoDB.insert(query)

    /* Check if the value has been added */
    if (mongoDB.count() == (initNb + 1))
      true
    else
      false
  }

  /**
    * Close the database connection
    */
  def closeConnection() = {
    mongoConn.close()
  }
}
