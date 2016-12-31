package controllers

import com.mongodb.casbah.commons.Logger
import com.tipz.app.TipzStack
import models.Account
import org.fusesource.scalate.util.Log
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class SessionController extends TipzStack {

  get("/signin") {

    /* Check if the user is already connected */
    val email : String = ""
    if (session.getAttribute("email") != null)
      redirect("/")

    val errorMessage = ""

    contentType="text/html"

    layoutTemplate("/WEB-INF/views/signin.jade",
      "user" -> "",
      "email" -> email,
      "errorMessage" -> errorMessage
    )
  }


  get("/signup") {
    /* Check if the user is already connected */
    if (session.getAttribute("email") != null)
      redirect("/")

    val errorMessage = ""
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/signup.jade",
      "user" -> "",
      "email" -> "",
      "lastname" -> "",
      "firstname" -> "",
      "errorMessage" -> errorMessage
    )
  }

  get("/logout") {
    session.setAttribute("email", null)
    redirect("/")
  }

  post("/signin") {
    var errorMessage : String = ""

    val email = params("email").toString
    val password = params("password").toString
    val accountModel = new Account

    val account = accountModel.findByEmail(email)
    accountModel.closeConnection()

    if (account.length == 0)
      errorMessage += "Account not found ! "
    else if (account(0).get("password") != password)
      errorMessage += "Password is incorrect ! "

    if (errorMessage == "") {
      session.setAttribute("email", email)
      redirect("/")
    }
    else {
      contentType="text/html"

      layoutTemplate("/WEB-INF/views/signin.jade",
        "user" -> "",
        "email" -> email,
        "errorMessage" -> errorMessage
      )
    }
  }

  post("/signup") {
    var errorMessage = ""

    val email = params("email").toString
    val lastname = params("lastname").toString
    val firstname = params("firstname").toString
    val password = params("password").toString

    val accountModel = new Account
    val isExist = accountModel.findByEmail(email)
    if (isExist.length > 0)
      errorMessage += "Account already exists ! "
    else {
      val res = accountModel.createUser(email, password, firstname, lastname)
      if (res == false)
        errorMessage += "Cannot create user ! "
    }
    accountModel.closeConnection()

    if (errorMessage == "") {
      session.setAttribute("email", email)
      redirect("/")
    }
    else {
      contentType="text/html"

      layoutTemplate("/WEB-INF/views/signin.jade",
        "user" -> "",
        "email" -> email,
        "lastname" -> lastname,
        "firstname" -> firstname,
        "errorMessage" -> errorMessage
      )
    }
  }

  get("/") {
    redirect("/")
  }
}
