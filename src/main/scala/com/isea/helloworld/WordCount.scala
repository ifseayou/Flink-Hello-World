package com.isea.helloworld

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}

object WordCount {
  def main(args: Array[String]): Unit = {
    // 创建执行环境
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    // 从文件中读取数据
    val inPath = "G:\\scala_learn\\Flink\\Flink-Hello-World\\src\\main\\resources\\hello.txt"

    // 将文本文件转为DataSet
    val inputDS: DataSet[String] = env.readTextFile(inPath)

    // 做word count的操作，这里的方法，groupBy(0)指的是按照二元组的第一个元素进行分组，按照第二个元素进行求和

    import org.apache.flink.api.scala._
    val wordCountDS: AggregateDataSet[(String, Int)] = inputDS.flatMap(_.split(" ")).map((_, 1)).groupBy(0).sum(1)

    // 输出
    wordCountDS.print()
  }
}
