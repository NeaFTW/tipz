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

  /**
    * url : /session/signin
    * Return the page for the authentification of the user
    */
  get("/signin") {
    /* Check if the user is already connected */
    val email : String = ""
    if (session.getAttribute("email") != null)
      redirect("/")

    val errorMessage = ""

    /* Rendering template */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/signin.jade",
      "user" -> "",
      "email" -> email,
      "errorMessage" -> errorMessage
    )
  }

  /**
    * url : /session/signup
    * Returns the page to create an user for the website
    */
  get("/signup") {
    /* Check if the user is already connected */
    if (session.getAttribute("email") != null)
      redirect("/")

    val errorMessage = ""

    /* Rendering template */
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/signup.jade",
      "user" -> "",
      "email" -> "",
      "lastname" -> "",
      "firstname" -> "",
      "errorMessage" -> errorMessage
    )
  }

  /**
    * url : /session/logout
    * logout the user
    */
  get("/logout") {
    session.setAttribute("email", null)
    redirect("/")
  }

  /**
    * post method
    * User log in. Check if the user exist. If true the add user email to the session otherwise return an error
    */
  post("/signin") {
    var errorMessage : String = ""

    /* Getting data from form */
    val email = params("email").toString
    val password = params("password").toString

    /* Getting account from the database*/
    val accountModel = new Account
    val account = accountModel.findByEmail(email)
    accountModel.closeConnection()

    /* Check if the account exist or not and check password if exist */
    if (account.isEmpty)
      errorMessage += "Account not found ! "
    else if (account(0).get("password") != password)
      errorMessage += "Password is incorrect ! "

    /* if there is no error, set the session with the account email and redirect to home otherwise send an error
    on the signin page*/
    if (errorMessage == "") {
      session.setAttribute("email", email)
      redirect("/")
    }
    else {
      /* Rendering template */
      contentType="text/html"

      layoutTemplate("/WEB-INF/views/signin.jade",
        "user" -> "",
        "email" -> email,
        "errorMessage" -> errorMessage
      )
    }
  }

  /**
    * post method
    * url : /session/signup
    * Creating account and sign in
    */
  post("/signup") {
    var errorMessage = ""

    /* Getting post data */
    val email = params("email").toString
    val lastname = params("lastname").toString
    val firstname = params("firstname").toString
    val password = params("password").toString

    /* Getting Account infos */
    val accountModel = new Account
    val isExist = accountModel.findByEmail(email)

    /* Check if the account exist or not*/
    if (isExist.length > 0)
      errorMessage += "Account already exists ! "
    else {
      /* Insert user into the database */
      val res = accountModel.createUser(email, password, firstname, lastname)
      if (res == false)
        errorMessage += "Cannot create user ! "
    }
    accountModel.closeConnection()

    if (errorMessage == "") {
      /* Log the user and redirect to home */
      session.setAttribute("email", email)
      redirect("/")
    }
    else {
      /* Send error to the signin page */
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

  /**
    * url : project/
    * redirecting to home
    */
  get("/") {
    redirect("/")
  }
}
