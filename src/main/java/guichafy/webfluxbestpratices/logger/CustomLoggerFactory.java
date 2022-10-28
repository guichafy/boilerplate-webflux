package guichafy.webfluxbestpratices.logger;

import org.slf4j.LoggerFactory;

public class CustomLoggerFactory {
    /**
     * Get an XLogger instance by name.
     *
     * @param name
     * @return XLogger instance
     */
    public static CustomCustomLogger getCustomLogger(String name) {
        return new CustomCustomLogger(LoggerFactory.getLogger(name));
    }

    /**
     * Get a new XLogger instance by class. The returned XLogger
     * will be named after the class.
     *
     * @param clazz
     * @return XLogger instance by name
     */
    public static CustomCustomLogger getCustomLogger(Class<?> clazz) {
        return getCustomLogger(clazz.getName());
    }
}
