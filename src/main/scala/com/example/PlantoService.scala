package com.example

import scala.collection.mutable._
import sys.process._
import com.mongodb.casbah.Imports._

class PlantoService {
  
  // To directly connect to the default server localhost on port 27017
  val mongoClient: MongoClient = MongoClient("localhost", 27017)
  val database: MongoDB = mongoClient("fantasy")
  
  // MongoDB collection
  val collection: MongoCollection = database("tasks")
  
  
  def register(plantoTask: PlantoTask) {
    println("Registering task: " + plantoTask)
    collection.insert(plantoTask.toDBObject)
  }
  
  def executeTask(plantoTask: PlantoTask): String = {
    plantoTask.command.!!
  }
  
  def listTasks(): List[PlantoTask] = {
    val buffer = ListBuffer[PlantoTask]();
    val docs = collection.find()
    for (doc <- docs) {
      val title = doc.getAsOrElse[String]("title", "no title")
      val command = doc.getAsOrElse[String]("command", "")
      buffer += new PlantoTask(title, command)
    }
    buffer.toList
  }
  
}