package io.github.wtog.strace.test

import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import io.github.wtog.strace.ThreadContextUtil
import io.github.wtog.strace.TraceExecuteContext._
import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.Random

/**
  * @author : tong.wang
  * @since : 2019-07-02 23:21
  * @version : 1.0.0
  */
class TraceTest extends FunSuite with LazyLogging {

  def m1(group: Int): ListBuffer[String] = {
    ThreadContextUtil()

    log(s"$group hh")

    val buffer = new ListBuffer[String]
    val pt = ThreadContextUtil.getContext.get.guid
    buffer.append(pt)
    Future {
      log(s"$group f1")

      val f1t = ThreadContextUtil.getContext.get.guid
      buffer.append(f1t)
      Future {
        log(s"$group f2")
        Thread.sleep(Random.nextInt(10))
        val f2t = ThreadContextUtil.getContext.get.guid
        buffer.append(f2t)
      }
    }.flatMap { _ =>
      val f3t = ThreadContextUtil.getContext.get.guid
      buffer.append(f3t)
      Future {
        val f4t = ThreadContextUtil.getContext.get.guid
        buffer.append(f4t)
      }
    }
    TimeUnit.MILLISECONDS.sleep(500)
    ThreadContextUtil.cleanContext()
    assert(buffer.toSet.size == 1)
    buffer
  }

  def log(msg: String) = logger.info(s"${msg} ${Thread.currentThread().getId}")

  test("log guid") {
    (1 to 10) foreach { g =>
      Future{
        m1(g)
      }(scala.concurrent.ExecutionContext.Implicits.global)
      println()
    }

    TimeUnit.SECONDS.sleep(10)
  }

}
