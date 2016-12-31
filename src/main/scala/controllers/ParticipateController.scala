package controllers

import com.mongodb.casbah.Imports
import com.tipz.app.TipzStack
import models.{AccountCounterpart, Counterpart, Project}
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class ParticipateController extends TipzStack {
  get("/:counterpartId") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val id : Int = Integer.parseInt(params("counterpartId"))

    val counterpartModel = new Counterpart
    val counterpartList = counterpartModel.findById(id)
    counterpartModel.closeConnection()

    if (counterpartList.length != 1)
      redirect("/")

    val counterpart : Imports.DBObject = counterpartList(0)

    val projectModel = new Project
    val counterpartId : Int = counterpart.get("id").toString.toFloat.toInt
    val project = projectModel.findProjectById(counterpartId)(0)

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

  get("/:counterpartId/submit") {
    var user = ""
    var participateErrorMessage = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val id = params("counterpartId").toString.toInt

    val counterpartModel = new Counterpart
    val counterpartList = counterpartModel.findById(id)
    counterpartModel.closeConnection()

    if (counterpartList.length != 1)
      redirect("/")

    val counterpart : Imports.DBObject = counterpartList(0)

    val projectModel = new Project
    val counterpartId : Int = counterpart.get("id").toString.toFloat.toInt
    val project = projectModel.findProjectById(counterpartId)(0)

    val accountCounterpartModel = new AccountCounterpart
    val isAlreadyBought = accountCounterpartModel.isAlreadyBought(counterpartId, user)

    println(isAlreadyBought)

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

  get("/") {
    redirect("/")
  }
}
