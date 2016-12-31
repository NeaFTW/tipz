package controllers

import com.mongodb.casbah.Imports
import com.tipz.app.TipzStack
import models.{AccountCounterpart, Counterpart, Project}
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class ParticipateController extends TipzStack {

  /**
    * url : participate/id
    * Get the page to participate to a project
    */
  get("/:counterpartId") {
    /* Check if the user is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val id : Int = Integer.parseInt(params("counterpartId"))

    /* Get the counterpart by its id */
    val counterpartModel = new Counterpart
    val counterpartList = counterpartModel.findById(id)
    counterpartModel.closeConnection()

    /* redirect to home is the counterpart doesn't exist */
    if (counterpartList.length != 1)
      redirect("/")

    val counterpart : Imports.DBObject = counterpartList(0)

    /* Get author information from project */
    val projectModel = new Project
    val counterpartId : Int = counterpart.get("id").toString.toFloat.toInt
    val project = projectModel.findProjectById(counterpartId)(0)

    /* Rendering template */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/participate.jade",
      "user" -> user,
      "participateErrorMessage" -> "",
      "counterpartValue" -> counterpart.get("value").toString.toFloat,
      "counterpartName" -> counterpart.get("name").toString,
      "projectAuthor" -> project.get("author"),
      "projectName" -> project.get("name")
    )
  }

  /**
    * url : participate/id/submit
    * Participate to an project by applying to a counterpart
    */
  get("/:counterpartId/submit") {
    /* Check if the user exists */
    var user = ""
    var participateErrorMessage = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    /* get counterpart from its id */
    val id = params("counterpartId").toString.toInt

    val counterpartModel = new Counterpart
    val counterpartList = counterpartModel.findById(id)
    counterpartModel.closeConnection()

    /* check if the counterpart exists */
    if (counterpartList.length != 1)
      redirect("/")

    val counterpart : Imports.DBObject = counterpartList(0)

    /* Get the project trought the counterpart */
    val projectModel = new Project
    val counterpartId : Int = counterpart.get("id").toString.toFloat.toInt
    val project = projectModel.findProjectById(counterpartId)(0)

    val accountCounterpartModel = new AccountCounterpart
    val isAlreadyBought = accountCounterpartModel.isAlreadyBought(counterpartId, user)

    /* Check if the user already apply to the counterpart. If false, add the participation into the database
    * otherwise send an error */
    if (isAlreadyBought == false) {
      val res = accountCounterpartModel.insertAccountCounterpart(user, counterpartId)
      accountCounterpartModel.closeConnection()
      if (res == true)
        redirect("/")
      else
        participateErrorMessage += "The purchase cannot be done ! contact the webmaster of the website ! "
    }
    else
      participateErrorMessage += "You already have bought this counterpart for this project ! "
    accountCounterpartModel.closeConnection()

    /* Rendering template */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/participate.jade",
      "user" -> user,
      "participateErrorMessage" -> participateErrorMessage,
      "counterpartValue" -> counterpart.get("value").toString.toFloat,
      "counterpartName" -> counterpart.get("name").toString,
      "projectAuthor" -> project.get("author"),
      "projectName" -> project.get("name")
    )
  }

  /**
    * url : project/
    * redirecting to home
    */
  get("/") {
    redirect("/")
  }
}
