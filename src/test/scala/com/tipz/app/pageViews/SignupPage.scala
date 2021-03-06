package com.tipz.app.pageViews

import controllers.SessionController
import org.scalatra.test.specs2.ScalatraSpec

/**
  * Created by root on 26/12/16.
  */
class SignupPage extends ScalatraSpec { def is =
  "GET / on SessionController"                     ^
    "should return status 200"                  ! root200^
    end

  addServlet(classOf[SessionController], "/*")

  def root200 = get("/signup") {
    status must_== 200
  }

}
