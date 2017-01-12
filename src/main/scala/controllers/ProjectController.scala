package controllers

import com.mongodb.casbah.Imports
import com.tipz.app.TipzStack
import models.{Account, Counterpart, Project}
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class ProjectController extends TipzStack {
  /**
    * url : /project/add
    * Returns the page to create a project
    */
  get("/add") {
    /* Check if the user is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val accountModel = new Account
    val account = accountModel.findByEmail(user)(0)
    accountModel.closeConnection()

    /* Rendering template */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/editProject.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectDescription" -> "",
      "projectName" -> "",
      "projectAuthor" -> (account.get("firstname") + " " + account.get("lastname")),
      "projectContact" -> user,
      "projectId" -> 0,
      "isEdit" -> 0
    )
  }

  /**
    * url : /project/id/
    * Return the project associated to the project with it's data
    */
  get("/:projectId/") {
    /* Check if the user is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString

    val projectIdStr = params("projectId")
    var testId = 0
    if (projectIdStr matches """\d+""")
      testId = projectIdStr.toInt

    if (testId == 0)
      redirect("/")
    val id = Integer.parseInt(projectIdStr)

    /* Get the project associated to the id */
    val projectModel = new Project
    val project = projectModel.findProjectById(id)(0)
    projectModel.closeConnection()

    /* Get the counterpart list associated to the project */
    val counterpartModel = new Counterpart
    val counterpartList = counterpartModel.findAllCounterpartsByProject(id)


    val contributorList = counterpartModel.findAllUserParticipationToProject(id)
    counterpartModel.closeConnection()

    /* Rendering template */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/project.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectId" -> id,
      "projectDescription" -> project.get("description"),
      "projectName" -> project.get("name"),
      "projectAuthor" -> project.get("author"),
      "projectContact" -> project.get("contact"),
      "projectCreation" -> project.get("creationDate"),
      "counterpartList" -> counterpartList,
      "participateList" -> contributorList
    )
  }

  /**
    * url : /project/id/edit/
    * Page to edit an existing project
    */
  get("/:projectId/edit") {
    /* Check if the user is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val projectIdStr = params("projectId")
    var testId = 0
    if (projectIdStr matches """\d+""")
      testId = projectIdStr.toInt

    if (testId == 0)
      redirect("/")

    /* Get the project associated to the id*/
    val id = Integer.parseInt(projectIdStr)
    val projectModel = new Project
    val res : List[Imports.DBObject] = projectModel.findProjectById(id)
    projectModel.closeConnection()

    /* Check if the project exists */
    if (res.isEmpty)
      redirect("/")

    /* Check if the project is owned by the user */
    if (res(0).get("accountEmail") != user)
      redirect("/403")

    /* Rendering template */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/editProject.jade",
      "user" -> user,
      "errorMessage" -> "",
      "projectDescription" -> res(0).get("description"),
      "projectName" -> res(0).get("name"),
      "projectAuthor" -> res(0).get("author"),
      "projectContact" -> res(0).get("contact"),
      "projectId" -> res(0).get("id").toString.toFloat.toInt,
      "isEdit" -> 1
    )
  }

  /**
    * url : /project/counterpart/save
    * Terminate the process of project creation
    */
  get("/add/:projectId/counterpart/save") {
    /* Check if the user is connected */
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    var errorMessage = ""

    val projectIdStr = params("projectId")
    var testId = 0
    if (projectIdStr matches """\d+""")
      testId = projectIdStr.toInt

    if (testId == 0)
      redirect("/")

    /* Get the project associated to the project ID */
    val id = Integer.parseInt(projectIdStr)
    val projectModel = new Project
    val res : List[Imports.DBObject] = projectModel.findProjectById(id)
    projectModel.closeConnection()

    /* Check if the project exist */
    if (res.isEmpty)
      redirect("/")

    /* Check if the project is owned to the user*/
    if (res(0).get("accountEmail") != user)
      redirect("/403")

    /* Redirect to the project page */
    val counterpartModel = new Counterpart
    val counterpartList = counterpartModel.findAllCounterpartsByProject(id)

    if (counterpartList.isEmpty)
      errorMessage += "The project must contain one counterpart to be valid ! "

    val contributorList = counterpartModel.findAllUserParticipationToProject(id)
    counterpartModel.closeConnection()

    if (errorMessage != "") {
      contentType="text/html"
      layoutTemplate("/WEB-INF/views/counterpartList.jade",
        "user" -> user,
        "projectId" -> id,
        "errorMessage" -> errorMessage,
        "counterpartDescription" -> "",
        "counterpartName" -> "",
        "counterpartValue" -> 0.0f,
        "counterpartList" -> counterpartList
      )
    }
    else {
      contentType="text/html"
      layoutTemplate("/WEB-INF/views/project.jade",
        "user" -> user,
        "errorMessage" -> "",
        "projectId" -> id,
        "projectDescription" -> res(0).get("description"),
        "projectName" -> res(0).get("name"),
        "projectAuthor" -> res(0).get("author"),
        "projectContact" -> res(0).get("contact"),
        "projectCreation" -> res(0).get("creationDate"),
        "counterpartList" -> counterpartList,
        "participateList" -> contributorList
      )
    }
  }

  /**
    * post method
    * url : /project/add
    * Creating the project into the database
    */
  post("/add") {
    /* Check if the user is connected */
    var user = ""
    var errorMessage = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    /* Getting parameters form the post form*/
    val name = params("name")
    val description = params("description")
    val author = params("author")
    val contact = params("contact")

    /* Check if the description have more than 140 caracters */
    if (description.length < 140)
      errorMessage += "The description must have more than 140 caracters ! "

    var res = 0

    /* Adding project into the database */
    if (errorMessage == "") {
      val projectModel = new Project
      res = projectModel.createProject(name, description, author, contact, user)
      projectModel.closeConnection()
    }

    if (res != 0) {
      /* Rendering template */
      val counterpartModel = new Counterpart
      val counterpartList : List[Imports.DBObject] = counterpartModel.findAllCounterpartsByProject(res)
      counterpartModel.closeConnection()

      /* Rendering template */
      contentType="text/html"

      layoutTemplate("/WEB-INF/views/counterpartList.jade",
        "user" -> user,
        "projectId" -> res,
        "errorMessage" -> "",
        "counterpartDescription" -> "",
        "counterpartName" -> "",
        "counterpartValue" -> 0.0f,
        "counterpartList" -> counterpartList
      )
    }
    else {
      /* Rendering template */
      contentType="text/html"

      layoutTemplate("/WEB-INF/views/editProject.jade",
        "user" -> user,
        "errorMessage" -> errorMessage,
        "projectId" -> 0,
        "projectDescription" -> description,
        "projectName" -> name,
        "projectAuthor" -> author,
        "projectContact" -> contact,
        "isEdit" -> 0
      )
    }
  }

  /**
    * url : /project/id/counterpart/add
    * Adding a counterpart to a project through the formular and return the list of counterparts in the same page
    */
  post("/add/:projectId/counterpart/add") {
    /* Check if the user is logged */
    var user = ""
    var errorMessage = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val projectIdStr = params("projectId")
    var testId = 0
    if (projectIdStr matches """\d+""")
      testId = projectIdStr.toInt

    if (testId == 0)
      redirect("/")

    /* Getting data from post form */
    val projectId : Int = Integer.parseInt(projectIdStr)
    val counterpartName = params("counterpartName")
    val counterpartDescription = params("counterpartDescription")
    val counterpartValue : Float = params("counterpartValue").toFloat

    /* Getting the projec from the database */
    val projectModel = new Project
    val res : List[Imports.DBObject] = projectModel.findProjectById(projectId)
    projectModel.closeConnection()

    /*  Check if the project exist */
    if (res.isEmpty)
      redirect("/")

    /* Check if the user own the project */
    if (res(0).get("accountEmail") != user)
      redirect("/403")

    /* Check if the value of the counterpart is > 0 */
    if (counterpartValue == 0.0f)
      errorMessage += "The counterpart amount cannot be 0"
    else if (counterpartValue < 0.0f)
      errorMessage += "The counterpart amount cannot be negative"

    /* Check insert the counterpart if there is no errors for values */
    val counterpartModel = new Counterpart
    if (errorMessage == "") {
      val res1 = counterpartModel.createCounterpart(counterpartName, counterpartValue, counterpartDescription, projectId)
      if (res1 != 0) {
        errorMessage += "Insertion of the counterpart failed ! "
      }
    }

    /* Getting counterpart list associated to the project */
    val counterpartList = counterpartModel.findAllCounterpartsByProject(projectId)
    counterpartModel.closeConnection()

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/counterpartList.jade",
      "user" -> user,
      "errorMessage" -> errorMessage,
      "counterpartDescription" -> "",
      "counterpartName" -> "",
      "counterpartValue" -> 0.0f,
      "projectId" -> res(0).get("id").toString.toFloat.toInt,
      "counterpartList" -> counterpartList
    )
  }

  /**
    * post method
    * url : /project/id/save
    * Update the project informations in the database
    */
  post("/:projectId/save") {
    /* Check if the user is connected or not */
    var user = ""
    var errorMessage = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    else
      redirect("/session/signin")

    val projectIdStr = params("projectId")
    var testId = 0
    if (projectIdStr matches """\d+""")
      testId = projectIdStr.toInt

    if (testId == 0)
      redirect("/")

    /* Getting parameters from post */
    val projectId = Integer.parseInt(projectIdStr)
    val description = params("description")
    val name = params ("name")
    val author = params("author")
    val contact = params("contact")

    /* Get the project into the database*/
    val projectModel = new Project
    val res : List[Imports.DBObject] = projectModel.findProjectById(projectId)
    projectModel.closeConnection()

    /* Check is the project exist */
    if (res.isEmpty)
      redirect("/")

    /* Check if the user is the owner of the project. If false send a 403 error */
    if (res(0).get("accountEmail") != user)
      redirect("/403")

    /* Check if the description have more than 140 caracters */
    if (description.length < 140)
      errorMessage += "The description must have more than 140 caracters ! "

    var check = false

    /* Insert updates into database */
    if (errorMessage == "") {
      val projectModel = new Project
      val res = projectModel.updateProject(projectId, name, description, author, contact)
      projectModel.closeConnection()

      /* if the update succeed, redirect to the project page */
      if (res == true)
        check = true
      else
        errorMessage += "An error occured during project update ! "
    }

    if (check) {
      val projectModel = new Project
      val res : List[Imports.DBObject] = projectModel.findProjectById(projectId)
      projectModel.closeConnection()

      val counterpartModel = new Counterpart
      val counterpartList = counterpartModel.findAllCounterpartsByProject(projectId)

      val contributorList = counterpartModel.findAllUserParticipationToProject(projectId)
      counterpartModel.closeConnection()

      contentType="text/html"
      layoutTemplate("/WEB-INF/views/project.jade",
        "user" -> user,
        "errorMessage" -> "",
        "projectId" -> projectId,
        "projectDescription" -> res(0).get("description"),
        "projectName" -> res(0).get("name"),
        "projectAuthor" -> res(0).get("author"),
        "projectContact" -> res(0).get("contact"),
        "projectCreation" -> res(0).get("creationDate"),
        "counterpartList" -> counterpartList,
        "participateList" -> contributorList
      )
    }
    else {
      /* Rendering template if there is an error */
      contentType="text/html"

      layoutTemplate("/WEB-INF/views/editProject.jade",
        "user" -> user,
        "projectId" -> projectId,
        "errorMessage" -> errorMessage,
        "projectDescription" -> description,
        "projectName" -> name,
        "projectAuthor" -> author,
        "projectContact" -> contact,
        "isEdit" -> 1
      )
    }
  }

  /**
    * url : project/
    * redirecting to home
    */
  get("/") {
    redirect("/")
  }
}
