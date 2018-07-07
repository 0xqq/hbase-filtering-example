package com.example

import java.util.Arrays

import com.example.Repository.TransactionRepository
import org.apache.hadoop.hbase.CompareOperator
import org.apache.hadoop.hbase.client.{Result, ResultScanner, Scan}
import org.apache.hadoop.hbase.filter._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.util.Bytes.toBytes


object App {
  def main(args: Array[String]): Unit = {
//    TransactionRepository.seedData(100)
    val filters: FilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL)

    filters.addFilter(Arrays.asList[Filter](
//      new ColumnCountGetFilter(3),
//      new PageFilter(5),
//      new ColumnPaginationFilter(2, 1),
//      new InclusiveStopFilter(toBytes(23.toString)),
//      fieldShouldEqualFilter("acctKey",Bytes.toBytes("4774785232413492367")),
//      inRangeOfFilter("date",toBytes("3540810929681912437"),toBytes("7540810929681912437"))
    ))


    val scan = new Scan().setFilter(filters)
    val resultScanner = TransactionRepository.getScanner(scan)
    printResult(resultScanner)
  }

  private def printResult(resultScanner: ResultScanner) = {
    val iterator = resultScanner.iterator()
    println("acctKey----------amount---------trancId----------DateInMs")
    while (iterator.hasNext) {
      val result: Result = iterator.next()
      print(Bytes.toString(result.getValue(TransactionRepository.columnFamily, toBytes("acctKey"))))
      print("    " + Bytes.toString(result.getValue(TransactionRepository.columnFamily, toBytes("amount"))))
      print("    " + Bytes.toString(result.getValue(TransactionRepository.columnFamily, toBytes("trancId"))))
      println("    " + Bytes.toString(result.getValue(TransactionRepository.columnFamily, toBytes("date"))))
    }
  }

  private def fieldShouldEqualFilter(fieldName:String,fieldValue: Array[Byte]) = new SingleColumnValueFilter(
    TransactionRepository.columnFamily,
    toBytes(fieldName),
    CompareOperator.EQUAL,
    fieldValue
  )

  private def greaterThanOrEqualToFilter(fieldName:String,fieldValue: Array[Byte]) = new SingleColumnValueFilter(
    TransactionRepository.columnFamily,
    toBytes(fieldName),
    CompareOperator.GREATER_OR_EQUAL,
    fieldValue
  )

  private def lessThanOrEqualToFilter(fieldName:String,fieldValue: Array[Byte]) = new SingleColumnValueFilter(
    TransactionRepository.columnFamily,
    toBytes(fieldName),
    CompareOperator.LESS_OR_EQUAL,
    fieldValue
  )

  private def inRangeOfFilter(fieldName:String,minLimit: Array[Byte], maxLimit: Array[Byte]) = {
    val filters = new FilterList(FilterList.Operator.MUST_PASS_ALL)
    filters.addFilter(greaterThanOrEqualToFilter(fieldName,minLimit))
    filters.addFilter(lessThanOrEqualToFilter(fieldName,maxLimit))
    filters
  }

}
