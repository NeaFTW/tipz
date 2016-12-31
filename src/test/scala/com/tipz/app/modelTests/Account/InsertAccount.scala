package com.tipz.app.modelTests.Account

import com.tipz.app.MyScalatraServlet
import models.Account
import org.scalatra.test.specs2.ScalatraSpec

/**
  * Created by root on 31/12/16.
  */
class InsertAccount extends ScalatraSpec { def is =
  "Account model InsertAccount"                     ^
    "should return must be true"                  ! insertAccount^
    end

  def insertAccount = {
    val accountModel = new Account
    val res = accountModel.createUser("nea@nea.fr", "123", "nea", "walker")
    res mustEqual true
  }

}
