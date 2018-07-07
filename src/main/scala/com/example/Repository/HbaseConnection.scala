package com.example.Repository

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory}

trait HbaseConnection {
  private val conf: Configuration = HBaseConfiguration.create()
  val connection: Connection = ConnectionFactory.createConnection(conf)
  println("got connection...")
}
