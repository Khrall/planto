package com.example
import spray.json._
import DefaultJsonProtocol._
import com.mongodb.casbah.Imports._

case class PlantoTask(title: String, command: String) {
  def toDBObject: MongoDBObject = {
    MongoDBObject("title" -> title, "command" -> command)
  }
}

object PlantoTask extends DefaultJsonProtocol {
  implicit val plantoTaskFormat = jsonFormat2(PlantoTask.apply)
}