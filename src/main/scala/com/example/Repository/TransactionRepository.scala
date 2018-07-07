package com.example.Repository

import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Put, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.util.Bytes.toBytes

import scala.util.Random

object TransactionRepository extends HbaseConnection{
  val transactionTable = connection.getTable(TableName.valueOf("transaction"))
  val columnFamily = toBytes("cf1")

  def seedData(rowSize:Int) = {
    val accountNumbers = generateAccountNumbers(rowSize)
    val acctNumbers = generateAccountNumbers(rowSize)
    (0 to rowSize).map(index => {
      val acctKey = acctNumbers(Random.nextInt(rowSize))
      transactionTable.put(putRow(columnFamily, acctKey, index))
    })
  }

  private def generateAccountNumbers(size: Int) = {
    var accounts = List[Long]()
    for (index <- 0 until size) accounts = accounts :+ RandomData.accountKey()
    accounts
  }

  private def putRow(columnFamily: Array[Byte], accountKey: Long, rowId: Int) = {
    new Put(toBytes(rowId.toString))
      .addColumn(columnFamily, toBytes("acctKey"), Bytes.toBytes(accountKey.toString))
      .addColumn(columnFamily, toBytes("trancId"), Bytes.toBytes(RandomData.trancId().toString))
      .addColumn(columnFamily, toBytes("amount"), Bytes.toBytes(RandomData.amount().toString))
      .addColumn(columnFamily, toBytes("date"), Bytes.toBytes(RandomData.dateTimeInMs().toString))
  }

  def getScanner(scan:Scan) = transactionTable.getScanner(scan)
}
