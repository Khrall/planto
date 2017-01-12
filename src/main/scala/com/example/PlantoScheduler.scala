package com.example

import akka.actor.{ActorSystem, Actor, Props}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class PlantoScheduler(system: ActorSystem) {
  val planto: PlantoService = new PlantoService()
  
  def start() {
    // Attempt to create some sort of scheduler
    val Tick = "tick"
    val tickActor = system.actorOf(Props(new Actor {
      def receive = {
        case Tick => println("ayyy")
      }
    }))
    
    system.scheduler.schedule(50 milliseconds, 5 seconds, tickActor, Tick)
  }
  
}