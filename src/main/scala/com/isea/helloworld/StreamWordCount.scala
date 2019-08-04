package com.isea.helloworld

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object StreamWordCount {
  def  main(args: Array[String]): Unit = {
    // 获取配置的相关端口
    val params: ParameterTool = ParameterTool.fromArgs(args)
    val host: String = params.get("host")
    val port: Int = params.getInt("port")

    // 创建执行环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    // source ,这里使用socket的方式进行
    val socketDS: DataStream[String] = env.socketTextStream(host,port)

    import org.apache.flink.api.scala._
    // 做word count的操作，这里的方法，groupBy(0)指的是按照二元组的第一个元素进行分组，按照第二个元素进行求和
    val streamDS: DataStream[(String, Int)] = socketDS.flatMap(_.split(" "))
      .filter(_.nonEmpty).map((_,1))
      .keyBy(0)
      .sum(1)

    // 输出
    streamDS.print()

    //streamDS.print().setParallelism(1) // 这里可以指定并行度
    
    // 启动executor
    env.execute("socket stream...")
  }
}
