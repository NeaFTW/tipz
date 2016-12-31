package controllers

import com.mongodb.casbah.Imports
import com.tipz.app.TipzStack
import models.{Account, Counterpart, Project}
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

    val counterpartList : List[Imports.DBObject] = Nil

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/counterpartList.jade",
      "user" -> user,
      "errorMessage" -> "",
      "counterpartDescription" -> "",
      "counterpartName" -> "",
      "counterpartValue" -> 0.0f,
      "counterpartList" -> counterpartList
    )
  }

  get("/:projectId/") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString

    val id = Integer.parseInt(params("projectId"))

    val projectModel = new Project
    val project = projectModel.findProjectById(id)(0)
    projectModel.closeConnection()

    val counterpartModel = new Counterpart
    val counterpartList = counterpartModel.findAllCounterpartsByProject(id)
    counterpartModel.closeConnection()

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/project.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectDescription" -> project.get("description"),
      "projectName" -> project.get("name"),
      "projectAuthor" -> project.get("author"),
      "projectContact" -> project.get("contact"),
      "counterpartList" -> counterpartList
    )
  }

  get("/:projectId/edit/") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val id = Integer.parseInt(params("projectId"))

    val projectModel = new Project
    val project = projectModel.findProjectById(id)(0)
    projectModel.closeConnection()

    if (project.get("contact") != user)
      redirect("/403")

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/editProject.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectDescription" -> project.get("description"),
      "projectName" -> project.get("name"),
      "projectAuthor" -> project.get("author"),
      "projectContact" -> project.get("contact")
    )
  }

  post("/add") {
    var user = ""
    var errorMessage = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val name = params("name")
    val description = params("description")
    val author = params("author")
    val contact = params("contact")

    if (description.length < 140)
      errorMessage += "The description must have more than 140 caracters ! "

    if (errorMessage == "") {
      val projectModel = new Project
      val res = projectModel.createProject(name, description, author, contact, user)
      redirect("/project/add/" + res + "/")
    }

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/editProject.jade",
      "user" -> user,
      "errorMessage" -> errorMessage,
      "projectDescription" -> description,
      "projectName" -> name,
      "projectAuthor" -> author,
      "projectContact" -> contact
    )

  }

  post("/add/:projectId/") {

  }

  post("/add/:projectId/counterpartAddToList") {

  }

  post("/add/:projectId/save") {

  }

  post("/:projectId/save") {

  }

  get("/") {
    redirect("/")
  }
}
