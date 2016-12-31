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
    val query = MongoDBObject("creationDate" -> 1)
    val res = mongoDB.find().sort(query).toList
    res
  }

  def findBestProjects () = {
    val query = MongoDBObject(
      "amount" -> -1,
      "creationDate" -> 1
    )
    val res = mongoDB.find().sort(query).limit(30).toList
    res
  }

  def findAllAccountProject (email : String) = {
    val query = MongoDBObject("accountEmail" -> email)
    val res = mongoDB.find(query).toList
    res
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
      initNb + 1
    else
      0
  }

  def updateProject (id : Int, name : String, description : String, author : String, contact : String) = {
    /* Creatng update query */
    val query = MongoDBObject ("id" -> id)
    val update = $set("name" -> name, "description" -> description, "author" -> author, "contact" -> contact)
    mongoDB.update(query, update)

    /* Getting project update to check if the update have been done */
    val project = this.findProjectById(id)(0)
    if (project.get("name") == name  && project.get("description") == description && project.get("author") == author
      && project.get("contact") == contact)
      true
    else
      false
  }

  def updateProjectCounterparts (projectId : Int) = {
    /* get the current number of participation*/
    val currentParticipate : Int = this.findProjectById(projectId)(0).get("participeNb").toString.toInt

    /* Creating update query */
    val query = MongoDBObject("id" -> projectId)
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
    val query = MongoDBObject("id" -> projectId)
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
