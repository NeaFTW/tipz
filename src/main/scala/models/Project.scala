package models

import com.mongodb.casbah.MongoConnection

/**
  * Created by root on 27/12/16.
  */
class Project {
  val mongoConn = MongoConnection()
  val mongoDB = mongoConn("tipz")("account")

  def closeConnection() = {
    mongoConn.close()
  }
}
