package controllers

import com.tipz.app.TipzStack
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class SessionController extends TipzStack {
  get("/signin") {
    <html>
      <body>
        <h1>Signin</h1>
      </body>
    </html>
  }


  get("/signup") {
    <html>
      <body>
        <h1>Signup</h1>
      </body>
    </html>
  }

  get("/logout") {
    <html>
      <body>
        <h1>Signout</h1>
      </body>
    </html>
  }

  post("/signin") {

  }

  post("/signup") {

  }
}
