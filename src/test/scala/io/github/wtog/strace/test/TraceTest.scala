package io.github.wtog.strace.test

import java.util.concurrent.{Executors, TimeUnit}

import io.github.wtog.strace.{ThreadContextUtil, TraceExecuteContext}
import org.scalatest.FunSuite
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author : tong.wang
  * @since : 2019-07-02 23:21
  * @version : 1.0.0
  */
class TraceTest extends FunSuite {

  private lazy val logger = LoggerFactory.getLogger(classOf[TraceTest])

  def m1(): Unit = {
    ThreadContextUtil()

    logger.info("hh")

    logger.info("ss")

    implicit val traceEc = TraceExecuteContext(ExecutionContext.fromExecutor(null))

    Future {
      logger.info("f1")

      Future {
        logger.info("f2")
      }
    }

    TimeUnit.MILLISECONDS.sleep(500)
    ThreadContextUtil.cleanContext()
  }

  def m2(): Unit = {
    m1()
  }

  test("trace") {
    m1()
    m2()
  }
}
