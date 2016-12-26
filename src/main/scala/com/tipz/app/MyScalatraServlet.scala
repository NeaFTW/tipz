package com.tipz.app

import org.scalatra._

class MyScalatraServlet extends TipzStack {

  get("/") {
    <html>
      <body>
        <h1>Index</h1>
      </body>
    </html>
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
