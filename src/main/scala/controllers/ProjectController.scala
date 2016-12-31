package controllers

import com.mongodb.casbah.Imports
import com.tipz.app.TipzStack
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class ProjectController extends TipzStack {
  get("/add") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/editProject.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectDescription" -> "",
      "projectName" -> "",
      "projectAuthor" -> "",
      "projectContact" -> ""
    )
  }

  get("/add/:projectId/") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/addCounterpart.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectDescription" -> "",
      "projectName" -> "",
      "projectAuthor" -> "",
      "projectContact" -> ""
    )
  }

  get("/:projectId/") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    val counterpartList = Nil

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/project.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectDescription" -> "",
      "projectName" -> "",
      "projectAuthor" -> "",
      "projectContact" -> "",
      "counterpartList" -> counterpartList
    )
  }

  get("/:projectId/edit/") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/editProject.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectDescription" -> "",
      "projectName" -> "",
      "projectAuthor" -> "",
      "projectContact" -> ""
    )
  }

  post("/add") {

  }

  post("/add/:projectId/") {

  }

  post("/counterpartAddToList") {

  }

  post("/counterpartsAdd") {

  }

  post("/:projectId/save") {

  }

  get("/") {
    redirect("/")
  }
}
