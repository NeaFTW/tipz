package models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject

/**
  * Created by root on 27/12/16.
  */
class AccountCounterpart {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("accountCounterpart")

  def findAll() ={
    val res = mongoDB.find().toList
    res
  }

  def findAllCounterpartBoughtByAccount(accountEmail : String) = {
    val query = MongoDBObject(
      "accountEmail" -> accountEmail
    )
    val res = mongoDB.find(query)
    res
  }

  def findAllAccountBoughtByCounterpart(counterpartId : Int) = {
    val query = MongoDBObject(
      "counterparId" -> counterpartId
    )
    val res = mongoDB.find(query)
  }

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
}
