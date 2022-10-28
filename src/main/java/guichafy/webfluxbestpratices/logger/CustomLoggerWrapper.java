package guichafy.webfluxbestpratices.logger;

import guichafy.webfluxbestpratices.logger.codes.LogCodes;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.spi.LocationAwareLogger;

import static net.logstash.logback.argument.StructuredArguments.kv;

public class CustomLoggerWrapper {

    // To ensure consistency between two instances sharing the same name
    // (homonyms) a LoggerWrapper should not contain any state beyond
    // the Logger instance it wraps.
    // Note that 'instanceofLAL' directly depends on Logger.
    // fqcn depend on the caller, but its value would not be different
    // between successive invocations of a factory class

    protected final Logger logger;
    final String fqcn;
    // is this logger instance a LocationAwareLogger
    protected final boolean instanceofLAL;

    public CustomLoggerWrapper(Logger logger, String fqcn) {
        this.logger = logger;
        this.fqcn = fqcn;
        if (logger instanceof LocationAwareLogger) {
            instanceofLAL = true;
        } else {
            instanceofLAL = false;
        }
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void doLog(String msg, LogCodes logCodes, Object... args) {
        if (!logger.isErrorEnabled())
            return;
        args = ArrayUtils.add(args, kv("code", logCodes));
        ((LocationAwareLogger) logger).log(null, fqcn, logCodes.getLevelCode(), msg, args, null);
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void doLog(String msg, LogCodes logCodes, Throwable t, Object... args) {
        if (!logger.isErrorEnabled())
            return;

        args = ArrayUtils.add(args, kv("code", logCodes));
        ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.ERROR_INT, msg,  args, t);
    }
}