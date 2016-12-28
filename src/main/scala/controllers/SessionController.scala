package controllers

import com.tipz.app.TipzStack
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class SessionController extends TipzStack {
  get("/signin") {
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/signin.jade",
      "user" -> ""
    )
  }


  get("/signup") {
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/signup.jade",
      "user" -> ""
    )
  }

  get("/logout") {

  }

  post("/signin") {

  }

  post("/signup") {

  }
}
