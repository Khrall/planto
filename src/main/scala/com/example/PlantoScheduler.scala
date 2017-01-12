package com.example

import cronish.dsl._

class PlantoScheduler() {
  
  def start() {
    val payroll = task {
      println("You have just been paid... Finally!")
    }
    payroll executes "every 4 seconds" exactly 4.times
  }
  
}