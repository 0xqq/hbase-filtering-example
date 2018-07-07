package com.example.Repository

import scala.util.Random

object RandomData {

  def accountKey() = Math.abs(Random.nextLong())

  def trancId() = Math.abs(Random.nextLong())

  def amount() = Math.abs(Random.nextDouble()*1000)

  def dateTimeInMs() = Math.abs(Random.nextLong())
}
