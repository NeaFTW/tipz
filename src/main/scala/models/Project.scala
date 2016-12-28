package models

import utils.DateGetter
import com.mongodb.casbah.{Imports, MongoConnection}
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.query.Imports._

/**
  * Created by root on 27/12/16.
  */
class Project {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("project")

  def findAllProjects() = {
    val res = mongoDB.find().toList
    res
  }

  def findProjectById (id : Int) = {
    val query = MongoDBObject("id" -> id)
    val res = mongoDB.find(query).toList
    res
  }

  def findAllIndexProjects () = {

  }

  def findBestProjects () = {

  }

  def findAllAccountProject (email : String) = {

  }

  def createProject (name : String, description : String, author : String, contact : String,
                     accountEmail : String) = {
    /* Getting the initial number of element into the collection*/
    val initNb = mongoDB.count()
    val dateGetter = new DateGetter()
    val date = dateGetter.currentDate()

    /* Creating the mongoObject of the project with the id equal to the number of element */
    val builder = MongoDBObject.newBuilder
    builder += "id" -> (initNb + 1)
    builder += "name" -> name
    builder += "creationDate" -> date
    builder += "description" -> description
    builder += "amount" -> 0
    builder += "author" -> author
    builder += "contact" -> contact
    builder += "participeNb" -> 0
    builder += "accountEmail" -> accountEmail
    val project = builder.result

    /* Insert data into the collection */
    mongoDB.insert(project)

    /* Check if the value has been added */
    if (initNb + 1 == mongoDB.count())
      true
    else
      false
  }

  def updateProject () = {

  }

  def updateProjectCounterparts (projectId : Int) = {
    /* get the current number of participation*/
    var currentParticipate : Int = this.findProjectById(projectId)(0).get("participeNb").toString.toInt

    /* Creating update query */
    val query = MongoDBObject({
      "id" -> projectId
    })
    val update = $set("participeNb" -> (currentParticipate + 1))
    /* update Database */
    mongoDB.update(query, update)

    /* Check if the update succeed or not */
    if (this.findProjectById(projectId)(0).get("participeNb").toString.toInt == (currentParticipate + 1))
      true
    else
      false
  }

  def updateProjectAmount (counterpartId : Int, amount : Float) = {
    /* getting the projecId associated to the counterpart*/
    val counterPartModel = new Counterpart
    val counterpart = counterPartModel.findById(counterpartId)(0)
    counterPartModel.closeConnection()
    val projectId = counterpart.get("projectId").toString.toInt

    /* Gettint project current amount and counterpart price*/
    val currentAmount = this.findProjectById(projectId)(0).get("amount").toString.toInt
    val counterpartPrice = counterpart.get("value").toString.toInt

    /* Creating update query */
    val query = MongoDBObject({
      "id" -> projectId
    })
    val update = $set("amount" -> (currentAmount + counterpartPrice))
    /* update the database */
    mongoDB.update(query, update)

    /* Check if the update succeed or not */
    if (this.findProjectById(projectId)(0).get("amount").toString.toInt == (currentAmount + counterpartPrice))
      true
    else
      false
  }

  def closeConnection() = {
    mongoConn.close()
  }
}
