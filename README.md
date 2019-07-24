# strace

print global id in application log file, now only support log4j2

## how to use

1. libraryDependencies in build.sbt

    ```sbt
    io.github.wtog.strace %% strace % 1.0.0-SNAPSHOT
    ```

2. log4j2.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration monitorInterval="30" status="warn" packages="org.apache.logging.log4j.core">
    <Appenders>
        <Console name="STDOUT" target="SYSTEM_OUT">
            <PatternLayout pattern="%date{yyyy-MM-dd HH:mm:ss.SSS} [%level] %logger %thread %G - %message%n%xException"/>
        </Console>
    </Appenders>

    <Loggers>
        <Root level="INFO" additivity="false">
            <appender-ref ref="STDOUT"/>
        </Root>
    </Loggers>
    </configuration>
    ```
    
    %G in PatternLayout is used to print global id

3. set Future Execute Context by 

    ```scala
    import io.github.wtog.strace.TraceExecuteContext._
    ```