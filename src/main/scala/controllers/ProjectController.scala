package controllers

import com.tipz.app.TipzStack
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class ProjectController extends TipzStack {
  get("/add") {
    <html>
      <body>
        <h1>Create project</h1>
      </body>
    </html>
  }

  get("/:projectId") {
    <html>
      <body>
        <h1>Project view {params("projectId")}</h1>
      </body>
    </html>
  }

  get("/:projectId/edit") {
    <html>
      <body>
        <h1>Project edit {params("projectId")}</h1>
      </body>
    </html>
  }

  post("/add") {

  }

  post("/counterpartAddToList") {

  }

  post("/counterpartsAdd") {

  }

  post("/:projectId/save") {

  }
}
