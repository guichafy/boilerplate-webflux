package guichafy.webfluxbestpratices.logger;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.spi.LocationAwareLogger;

import static net.logstash.logback.argument.StructuredArguments.kv;

public class SampleLogger {


    private final Logger logger;

    private final String fqcn;

    private final boolean instanceofLAL;

    /**
     * Given an underlying logger, construct an CustomLogger
     *
     * @param logger underlying logger
     */
    public SampleLogger(Logger logger) {
        this.fqcn = SampleLogger.class.getName();
        this.logger = logger;
        if (logger instanceof LocationAwareLogger) {
            instanceofLAL = true;
        } else {
            instanceofLAL = false;
        }
    }


    public void doLog(LogCode logCode, Object... args) {
        args = ArrayUtils.add(args, kv("code", logCode.getCode()));
        ((LocationAwareLogger) logger).log(null, fqcn, logCode.getLevelCode(), logCode.getMessage(), args, null);
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void doLog(String msg, LogCode logCode, Object... args) {
        args = ArrayUtils.add(args, kv("code", logCode.getCode()));
        ((LocationAwareLogger) logger).log(null, fqcn, logCode.getLevelCode(), msg, args, null);
    }

    /**
     * Delegate to the appropriate method of the underlying logger.
     */
    public void doLog(String msg, LogCode logCode, Throwable t, Object... args) {
        args = ArrayUtils.add(args, kv("code", logCode.getCode()));
        ((LocationAwareLogger) logger).log(null, fqcn, LocationAwareLogger.ERROR_INT, msg,  args, t);
    }






}
