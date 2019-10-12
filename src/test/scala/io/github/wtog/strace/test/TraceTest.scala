package io.github.wtog.strace.test

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
<<<<<<< HEAD
import io.github.wtog.strace.extend.akka.TracePropagatingDispatcher
=======
>>>>>>> ae8c27bbf259b0d4238477d9e310ee4d82635702
import io.github.wtog.strace.{ThreadContextUtil, TraceExecuteContext}
import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

/**
  * @author : tong.wang
  * @since : 2019-07-02 23:21
  * @version : 1.0.0
  */
class TraceTest extends FunSuite with LazyLogging {

  def m1(group: Int)(implicit ec: ExecutionContext): ListBuffer[String] = {
    ThreadContextUtil.init()

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

  def execute(implicit ec: ExecutionContext) = {
    (1 to 10) foreach { i =>
      Future {
        m1(i)
      }(scala.concurrent.ExecutionContext.Implicits.global)
    }
    TimeUnit.SECONDS.sleep(3)
  }

  test("log guid") {
    execute(TraceExecuteContext.traceExecuteContext)
  }

  test("custom execution context") {
    val actor = ActorSystem("custom-execution-context")
    implicit val customExecutionContext = actor.dispatchers.lookup("akka.actor.cpu-dispatcher")
    assert(customExecutionContext.id == "akka.actor.cpu-dispatcher")
    execute(customExecutionContext)
  }
}
