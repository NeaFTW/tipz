package models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject

/**
  * Created by root on 27/12/16.
  */
class Counterpart {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("counterpart")

  def findAllCounterparts() = {
    val res = mongoDB.find().toList
    res
  }

  def findById(id : Int) = {
    val query = MongoDBObject("id" -> id)
    val res = mongoDB.find(query).toList
    res
  }

  def createCounterpart(name : String, value : Float, description : String, projectId : Int) = {
    /* Getting the initial size of the collection */
    val initNb = mongoDB.count()

    /* Creating the mongoObject of the user */
    val builder = MongoDBObject.newBuilder
    builder += "id" -> (initNb + 1)
    builder += "name" -> name
    builder += "value" -> value
    builder += "description" -> description
    builder += "projectId" -> projectId
    val user = builder.result

    /* Insert data into the collection */
    mongoDB.insert(user)

    /* Check if the value has been added */
    if (initNb + 1 == mongoDB.count())
      initNb + 1
    else
      0
  }

  def closeConnection() = {
    mongoConn.close()
  }
}
