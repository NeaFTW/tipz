package com.tipz.app.modelTests.Account

import com.tipz.app.MyScalatraServlet
import models.Account
import org.scalatra.test.specs2.ScalatraSpec

/**
  * Created by root on 31/12/16.
  */
class FindAccountFail extends ScalatraSpec { def is =
  "Account model find Account fail"                     ^
    "should return list must be empty"                  ! findAccount^
    end

  def findAccount = {
    val accountModel = new Account
    val res = accountModel.findByEmail("n@nea.fr")
    res.isEmpty mustEqual true
  }

}
