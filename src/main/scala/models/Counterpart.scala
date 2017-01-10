package models

import com.mongodb.casbah.{Imports, MongoClient, MongoConnection}
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
    val order = MongoDBObject("value" -> 1)
    val res = mongoDB.find(query).sort(order).toList
    res
  }

  /**
    * Function that will return all the contributor of a project
    * @param projectId
    * @return return a list containing email an conterpart amount
    */
  def findAllUserParticipationToProject (projectId : Int) = {
    /* Getting counterpart list with the same projectID*/
    val accountCounterpart = new AccountCounterpart
    val counterpartByProjectId = this.findAllCounterpartsByProject(projectId)
    /* Creating an empty list for result */
    var res = List[Imports.DBObject]()
    for (i <- 0 until counterpartByProjectId.length) {
      val counterpartId = counterpartByProjectId(i).get("id").toString.toFloat.toInt
      val accountList = accountCounterpart.findAllAccountBoughtByCounterpart(counterpartId)
      for (j <- 0 until accountList.length) {
        /* Create a new object containing the email and the value  of a the contributor and the contribution */
        var tmp = MongoDBObject.newBuilder
        tmp += "email" -> accountList(j).get("accountEmail")
        tmp += "value" -> counterpartByProjectId(i).get("value").toString.toFloat
        val obj = tmp.result()
        res ::=  obj
      }
    }
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
