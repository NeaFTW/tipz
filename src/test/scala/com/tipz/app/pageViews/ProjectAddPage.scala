package com.tipz.app.pageViews

/**
  * Created by root on 26/12/16.
  */
import controllers.ProjectController
import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class ProjectAddPage extends ScalatraSpec { def is =
  "GET / on ProjectController"                     ^
    "should return status 200"                  ! root200^
    end

  addServlet(classOf[ProjectController], "/*")

  def root200 = get("/add") {
    status must_== 200
  }
}
