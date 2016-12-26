package com.tipz.app.pageViews

/**
  * Created by root on 26/12/16.
  */

import com.tipz.app.MyScalatraServlet
import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class BestPage extends ScalatraSpec { def is =
  "GET / on MyScalatraServlet"                     ^
    "should return status 200"                  ! root200^
    end

  addServlet(classOf[MyScalatraServlet], "/*")

  def root200 = get("/best") {
    status must_== 200
  }
}
