package com.tipz.app.modelTests.Account

import com.tipz.app.MyScalatraServlet
import models.Account
import org.scalatra.test.specs2.ScalatraSpec

/**
  * Created by root on 31/12/16.
  */
class FindAccountSuccess extends ScalatraSpec { def is =
    "Account model Find success"                     ^
    "should return length notEqual 0"                  ! findAccount^
    end

  def findAccount = {
    val accountModel = new Account
    accountModel.createUser("nea@nea.fr", "123", "nea", "walker")
    val res = accountModel.findByEmail("nea@nea.fr")
    res.length mustNotEqual 0
  }

}
