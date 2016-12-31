package com.tipz.app

import com.mongodb.casbah.Imports
import models.Project
import org.scalatra._

class MyScalatraServlet extends TipzStack {

  get("/") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString

    val projectModel = new Project
    val projectList : List[Imports.DBObject] = projectModel.findAllIndexProjects()
    projectModel.closeConnection()

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/index.jade",
      "user" -> user,
      "projectList" -> projectList
    )
  }

  get("/best") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    contentType="text/html"

    val projectModel = new Project
    val projectList : List[Imports.DBObject] = projectModel.findBestProjects()
    projectModel.closeConnection()

    layoutTemplate("/WEB-INF/views/index.jade",
      "user" -> user,
      "projectList" -> projectList
    )
  }

  get("/myProjects") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val projectModel = new Project
    val projectList : List[Imports.DBObject] = projectModel.findAllAccountProject(user)
    projectModel.closeConnection()

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/myProjects.jade",
      "user" -> user,
      "projectList" -> projectList
    )
  }
}
