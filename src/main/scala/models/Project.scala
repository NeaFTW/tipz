package models

import java.text.SimpleDateFormat
import java.util.Date

import utils.DateGetter
import com.mongodb.casbah.{Imports, MongoClient, MongoConnection}
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.query.Imports._

/**
  * Created by root on 27/12/16.
  */
class Project {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("project")

  /**
    * Get all project into the database
    * @return List of the projects
    */
  def findAllProjects() = {
    val res = mongoDB.find().toList
    res
  }

  /**
    * Get the project by ots id
    * @param id
    * @return List of project containing the project
    */
  def findProjectById (id : Int) = {
    val query = MongoDBObject("id" -> id)
    val res = mongoDB.find(query).toList
    res
  }

  /**
    * Get all project ordered by date
    * @return List of projects ordered by date
    */
  def findAllIndexProjects () = {
    val query = MongoDBObject("creationDate" -> -1, "name" -> 1)
    val res = mongoDB.find().sort(query).toList
    res
  }

  /**
    * Get the 30 best projects
    * @return List of the 30 best projects
    */
  def findBestProjects () = {
    val query = MongoDBObject(
      "weight" -> -1,
      "creationDate" -> 1
    )
    val res = mongoDB.find().sort(query).limit(30).toList
    res
  }

  /**
    * Create projects and insert data into the database
    * @param name
    * @param description
    * @param author
    * @param contact
    * @param accountEmail
    * @return the id of the project
    */
  def createProject (name : String, description : String, author : String, contact : String,
                     accountEmail : String) = {
    /* Getting the initial number of element into the collection*/
    val initNb = mongoDB.count()
    val dateFormatter = new SimpleDateFormat("yyyy/MM/dd")
    val submittedDateConvert = new Date()
    val date = dateFormatter.format(submittedDateConvert)

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
    builder += "weight" -> 0
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

  /**
    * Update the project infos into the database
    * @param id
    * @param name
    * @param description
    * @param author
    * @param contact
    * @return true or false
    */
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

  /**
    * Update the number of participant into the database
    * @param projectId
    * @return true or false
    */
  def updateProjectCounterparts (projectId : Int) = {
    /* get the current number of participation*/

    val currentParticipate : Int = this.findProjectById(projectId)(0).get("participeNb").toString.toFloat.toInt

    /* Creating update query */
    val query = MongoDBObject("id" -> projectId)
    val update = $set("participeNb" -> (currentParticipate + 1))
    /* update Database */
    mongoDB.update(query, update)

    /* Check if the update succeed or not */
    if (this.findProjectById(projectId)(0).get("participeNb").toString.toFloat.toInt == (currentParticipate + 1))
      true
    else
      false
  }

  /**
    * Update the project total amount
    * @param counterpartId
    * @param amount
    * @return true or false
    */
  def updateProjectAmount (counterpartId : Int, amount : Float) = {
    /* getting the projecId associated to the counterpart*/
    val counterPartModel = new Counterpart
    val counterpart = counterPartModel.findById(counterpartId)(0)
    counterPartModel.closeConnection()
    val projectId = counterpart.get("projectId").toString.toFloat.toInt

    /* Gettint project current amount and counterpart price*/
    val currentAmount = this.findProjectById(projectId)(0).get("amount").toString.toFloat
    val counterpartPrice = counterpart.get("value").toString.toFloat

    /* Creating update query */
    val query = MongoDBObject("id" -> projectId)
    val update = $set("amount" -> (currentAmount + counterpartPrice))
    /* update the database */
    mongoDB.update(query, update)

    /* Check if the update succeed or not */
    if (this.findProjectById(projectId)(0).get("amount").toString.toFloat != currentAmount)
      true
    else
      false
  }

  /**
    * Function that update the project weight
    * @param projectId
    * @return
    */
  def updateProjectweight (projectId : Int) = {
    /* getting the projecId associated to the counterpart*/
    val project = this.findProjectById(projectId)(0)
    val amount = project.get("amount").toString.toFloat
    val nbParticipate = project.get("participeNb").toString.toFloat.toInt

    /* Creating update query */
    val query = MongoDBObject("id" -> projectId)
    val update = $set("weight" -> (amount * nbParticipate))
    /* update the database */
    mongoDB.update(query, update)

    /* Check if the update succeed or not */
    if (this.findProjectById(projectId)(0).get("amount").toString.toFloat != project.get("weight").toString.toFloat)
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
