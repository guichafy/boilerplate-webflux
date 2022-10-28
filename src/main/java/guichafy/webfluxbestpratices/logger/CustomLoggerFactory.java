package guichafy.webfluxbestpratices.logger;

import org.slf4j.LoggerFactory;

public class CustomLoggerFactory {
    /**
     * Get an XLogger instance by name.
     *
     * @param name
     * @return XLogger instance
     */
    public static CustomLogger getCustomLogger(String name) {
        return new CustomLogger(LoggerFactory.getLogger(name));
    }

    /**
     * Get a new XLogger instance by class. The returned XLogger
     * will be named after the class.
     *
     * @param clazz
     * @return XLogger instance by name
     */
    public static CustomLogger getCustomLogger(Class<?> clazz) {
        return getCustomLogger(clazz.getName());
    }
}
