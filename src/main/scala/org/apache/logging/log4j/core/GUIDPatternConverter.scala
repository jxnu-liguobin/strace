package org.apache.logging.log4j.core

import java.lang

import io.github.wtog.strace.ThreadContextUtil
import org.apache.logging.log4j.core.config.plugins.Plugin
import org.apache.logging.log4j.core.pattern.{ ConverterKeys, LogEventPatternConverter, PatternConverter }

/**
  * @author : tong.wang
  * @since : 2019-07-03 08:25
  * @version : 1.0.0
  */
@Plugin(name = "GUIDPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys(value = Array[String]("G"))
class GUIDPatternConverter(name: String, style: String) extends LogEventPatternConverter(name, style) {

  override def format(event: LogEvent, builder: lang.StringBuilder): Unit = {
   Option(ThreadContextUtil.getContext).foreach(context => builder.append(context.guid))
  }
}

object GUIDPatternConverter {
  def newInstance() = new GUIDPatternConverter(name = "guid", style = "guid")
}
