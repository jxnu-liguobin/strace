package io.github.wtog.strace.test

import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import io.github.wtog.strace.ThreadContextUtil
import io.github.wtog.strace.TraceExecuteContext._
import org.scalatest.FunSuite

import scala.concurrent.Future

/**
  * @author : tong.wang
  * @since : 2019-07-02 23:21
  * @version : 1.0.0
  */
class TraceTest extends FunSuite with LazyLogging {

  def m1(): Unit = {
    ThreadContextUtil()

    logger.info(s"hh")

    Future {
      logger.info(s"f1")

      Future {
        logger.info(s"f2")
      }
    }

    TimeUnit.MILLISECONDS.sleep(500)
    ThreadContextUtil.cleanContext()
  }

  test("log guid") {
    m1()
  }
}
