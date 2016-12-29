package com.tipz.app

import org.scalatra._

class MyScalatraServlet extends TipzStack {

  get("/") {
    var user = ""
    if (session.getAttribute("email") != null)
      user = session.getAttribute("email").toString
    contentType="text/html"

    layoutTemplate("/WEB-INF/views/hello-scalate.jade",
      "test" -> "Heignwiehg reignreouig rgirngiu eruig",
      "user" -> user
    )
  }

  get("/best") {
    <html>
      <body>
        <h1>Best</h1>
      </body>
    </html>
  }

  get("/myProjects") {
    <html>
      <body>
        <h1>My projects</h1>
      </body>
    </html>
  }
}
