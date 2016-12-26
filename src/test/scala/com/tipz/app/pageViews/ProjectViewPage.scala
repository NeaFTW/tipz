package com.tipz.app.pageViews

import controllers.ProjectController
import org.scalatra.test.specs2.ScalatraSpec

/**
  * Created by root on 26/12/16.
  */
class ProjectViewPage  extends ScalatraSpec { def is =
  "GET / on ProjectController"                     ^
    "should return status 200"                  ! root200^
    end

  addServlet(classOf[ProjectController], "/*")

  def root200 = get("/1") {
    status must_== 200
  }
}
