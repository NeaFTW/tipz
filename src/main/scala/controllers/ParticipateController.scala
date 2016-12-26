package controllers

import com.tipz.app.TipzStack
import org.scalatra._

/**
  * Created by root on 26/12/16.
  */
class ParticipateController extends TipzStack {
  get("/:counterpartId") {
    <html>
      <body>
        <h1>View counterpart</h1>
      </body>
    </html>
  }

  post("/:counterpartId/submit") {

  }
}
