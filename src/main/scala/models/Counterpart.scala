package models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject

/**
  * Created by root on 27/12/16.
  */
class Counterpart {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("counterpart")

  /**
    * Get all counterparts
    * @return the list of the projects
    */
  def findAllCounterparts() = {
    val res = mongoDB.find().toList
    res
  }

  /**
    * Get the list of the counterpart associated to an id
    * @param id
    * @return the list containing the project
    */
  def findById(id : Int) = {
    val query = MongoDBObject("id" -> id)
    val res = mongoDB.find(query).toList
    res
  }

  /**
    * Get all counterparts associated to a project
    * @param projectId
    * @return the list of the project
    */
  def findAllCounterpartsByProject(projectId : Int) = {
    val query = MongoDBObject("projectId" -> projectId)
    val res = mongoDB.find(query).toList
    res
  }

  /**
    * Insert a new counterpart in the database
    * @param name
    * @param value
    * @param description
    * @param projectId
    * @return the id of the counterpart
    */
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

  /**
    * Close the connection to the database
    */
  def closeConnection() = {
    mongoConn.close()
  }
}
