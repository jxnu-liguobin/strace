package io.github.wtog.strace

import scala.concurrent.ExecutionContext

/**
  * @author : tong.wang
  * @since : 2019-07-02 07:04
  * @version : 1.0.0
  */
case class TraceExecuteContext(executionContext: ExecutionContext) extends ExecutionContext {

  override def execute(runnable: Runnable): Unit = {
    val ct = ThreadContextUtil.getContext
    executionContext.execute(new Runnable {
      def run(): Unit = {
        ThreadContextUtil.copyContext(ct.copy(tid = Thread.currentThread().getId.toString))
        runnable.run()
      }
    })
  }

  override def reportFailure(cause: Throwable): Unit = executionContext.reportFailure(cause)

}
