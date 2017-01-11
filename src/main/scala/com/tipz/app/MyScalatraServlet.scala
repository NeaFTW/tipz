package com.tipz.app

import com.mongodb.casbah.Imports
import models.{AccountCounterpart, Project}
import org.scalatra._

class MyScalatraServlet extends TipzStack {

  /**
    * url : /
    * Index of the website
    */
  get("/") {
    /* Getting user email if this one is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString

    /* Getting the project list ordered by creation date */
    val projectModel = new Project
    val projectList : List[Imports.DBObject] = projectModel.findAllIndexProjects()
    projectModel.closeConnection()

    /* Rendering template with user parameter and project list */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/index.jade",
      "user" -> user,
      "projectList" -> projectList,
      "isBest" -> 0
    )
  }

  /**
    * url : /best
    * List of the best project
    */
  get("/best") {
    /* Getting user email if this one is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString

    /* Getting the project list ordered by total amount * number of user that participate to the project */
    val projectModel = new Project
    val projectList : List[Imports.DBObject] = projectModel.findBestProjects()
    projectModel.closeConnection()

    /* Rendering template with user parameter and project list */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/index.jade",
      "user" -> user,
      "projectList" -> projectList,
      "isBest" -> 1
    )
  }

  /**
    * url : /myProject
    * List of all project that a user have participated
    */
  get("/myProjects") {
    /* Getting user email if this one is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    /* Getting the project list of the project that the user have participated */
    val accountCounterpartModel = new AccountCounterpart
    val myCounterpartParticipation = accountCounterpartModel.findAllCounterpartBoughtByAccount(user)
    val projectModel = new Project
    var projectArray : List[Imports.DBObject] = List()
    for (counterpart <- myCounterpartParticipation) {
      val id = counterpart.get("counterpartId").toString.toFloat.toInt
      val project: Imports.DBObject = projectModel.findProjectById(id)(0)
      projectArray :+= project
    }
    projectModel.closeConnection()

    /* Rendering template with user parameter and project list */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/myProjects.jade",
      "user" -> user,
      "projectList" -> projectArray.toList
    )
  }

  /**
    * url : /403
    * forbiden page
    */
  get("/403") {
    /* Getting user email if this one is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString

    /* Rendering template with the email of the user if the user is connected */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/error403.jade",
      "user" -> user
    )
  }
}
