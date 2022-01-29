import PatternLayoutEncoder

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

//root(INFO, ['STDOUT'])
root(ch.qos.logback.classic.Level.OFF, ['STDOUT'])
