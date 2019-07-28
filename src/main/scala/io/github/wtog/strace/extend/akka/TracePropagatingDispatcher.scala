package io.github.wtog.strace.extend.akka

import java.util.concurrent.TimeUnit

import akka.dispatch.{ Dispatcher, DispatcherPrerequisites, ExecutorServiceFactoryProvider, MessageDispatcher, MessageDispatcherConfigurator }
import com.typesafe.config.Config
import io.github.wtog.strace.TraceExecuteContext

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * @author : tong.wang
  * @since : 2019-07-24 19:12
  * @version : 1.0.0
  */
class TracePropagatingDispatcher(
    config: MessageDispatcherConfigurator,
    id: String,
    throughput: Int,
    throughputDeadlineTime: Duration,
    executorServiceFactoryProvider: ExecutorServiceFactoryProvider,
    shutdownTimeout: FiniteDuration)
    extends Dispatcher(config, id, throughput, throughputDeadlineTime, executorServiceFactoryProvider, shutdownTimeout) {
  self =>

  override def prepare(): ExecutionContext = TraceExecuteContext.traceExecuteContext

  override def reportFailure(t: Throwable): Unit = self.reportFailure(t)
}

class TracePropagatingDispatcherConfigurator(config: Config, prerequisites: DispatcherPrerequisites) extends MessageDispatcherConfigurator(config, prerequisites) {
  val instance = new TracePropagatingDispatcher(
    this,
    id = config.getString("id"),
    throughput = config.getInt("throughput"),
    throughputDeadlineTime = config.getDuration("throughput-deadline-time", TimeUnit.NANOSECONDS).nanosecond,
    executorServiceFactoryProvider = configureExecutor(),
    shutdownTimeout = config.getDuration("shutdown-timeout", TimeUnit.MICROSECONDS).millisecond
  )

  override def dispatcher(): MessageDispatcher = instance

}
