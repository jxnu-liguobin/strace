package io.github.wtog.strace

import java.util.UUID

/**
  * @author : tong.wang
  * @since : 2019-07-02 07:08
  * @version : 1.0.0
  */
object ThreadContextUtil {
  
  private[this] val currentThread = new ThreadLocal[ThreadContext]()

  private[this] def sourceId = UUID.randomUUID().toString.replace("-", "")

  def apply(uuid: Option[String] = None) = {
    val ct = Thread.currentThread()
    currentThread.set(ThreadContext(uuid.getOrElse(sourceId), String.valueOf(ct.getId)))
    this
  }

  def copyContext(threadContext: ThreadContext) = {
    this.currentThread.set(threadContext)
  }

  def getContext = currentThread.get()

  def cleanContext() = currentThread.remove()
}

case class ThreadContext (uid: String, tid: String)

