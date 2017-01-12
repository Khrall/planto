package com.example

import akka.actor.Actor
import spray.routing.HttpService
import spray.json._
import DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport._
import spray.http.MediaTypes

case class Stuff(id: Int, data: String)

object Stuff extends DefaultJsonProtocol {
  implicit val stuffFormat = jsonFormat2(Stuff.apply)
}


class SampleServiceActor extends Actor with SampleRoute {
  def actorRefFactory = context
  def receive = runRoute(route)
}

trait SampleRoute extends HttpService {
  val planto: PlantoService = new PlantoService()
    
  val route = {
    path("execute") {
      post {
        entity(as[PlantoTask]) { plantoTask =>
          complete(planto.executeTask(plantoTask))
        }
      }
    } ~
    path("tasks") {
      respondWithMediaType(MediaTypes.`application/json`) {
        get {
          complete(planto.listTasks())
        }
      }
    } ~
    path("new-task") {
      post {
        entity(as[PlantoTask]) { plantoTask =>
          planto.register(plantoTask)
          complete("Task received")
        }
      }
    } ~
    path("stuff") {
      respondWithMediaType(MediaTypes.`application/json`) {
        get {
          complete(Stuff(1, "my stuff"))
        } ~
        post {
          entity(as[Stuff]) { stuff =>
            complete(Stuff(stuff.id + 100, stuff.data + " posted"))
          }
        }
      }
    } ~
    get {
      complete("Default GET")
    }
  }
}

