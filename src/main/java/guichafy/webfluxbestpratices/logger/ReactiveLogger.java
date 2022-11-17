package guichafy.webfluxbestpratices.logger;

import guichafy.webfluxbestpratices.context.RequestContext;
import org.slf4j.MDC;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ReactiveLogger {

    /**
     * Private constructor. Only static methods.
     */
    private ReactiveLogger() {
    }

    /**
     * Low level logger that considers the reactive signal to determine if the logger must be invoked or not.
     * If the logger is to be invoked, then the {@link RequestContext} is extracted from the reactive context
     * to update MDC with all the context properties. The MDC is cleared afterwards.
     *
     * @param isSignal
     * @param log
     * @return consumer of the reactive signal to log
     */
    public static <T> Consumer<Signal<T>> logOnSignal(Predicate<Signal<T>> isSignal, Consumer<Signal<T>> log) {
        return signal -> {
            if (!isSignal.test(signal)) {
                return;
            }
            try {
                RequestContext logContext = signal.getContextView().getOrDefault(RequestContext.class, new RequestContext());
                MDC.setContextMap(logContext.getContextMap());
                log.accept(signal);
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * Logger triggered with the signal type {@link SignalType#ON_NEXT}.
     *
     * @param log
     * @return consumer of the reactive signal to log
     */
    public static <T> Consumer<Signal<T>> logOnNext(Consumer<T> log) {
        return logOnSignal(
                signal -> signal.getType() == SignalType.ON_NEXT,
                signal -> log.accept(signal.get()));
    }

    /**
     * Logger triggered with the signal type {@link SignalType#ON_COMPLETE}.
     *
     * @param log
     * @return consumer of the reactive signal to log
     */
    public static <T> Consumer<Signal<T>> logOnComplete(Runnable log) {
        return logOnSignal(
                signal -> signal.getType() == SignalType.ON_COMPLETE,
                signal -> log.run());
    }

    /**
     * Logger triggered with the signal type {@link SignalType#ON_ERROR}.
     *
     * @param log
     * @return consumer of the reactive signal to log
     */
    public static <T> Consumer<Signal<T>> logOnError(Consumer<Throwable> log) {
        return logOnSignal(
                signal -> signal.getType() == SignalType.ON_ERROR,
                signal -> log.accept(signal.getThrowable()));
    }

    /**
     * Helper to log when there is no reactive step yet (e.g. inside an onErrorResume step
     * where it is required to log a message but including the log context).
     *
     * <code>
     * .onErrorResume(MyException.class, e -> ReactiveLogger.log(()
     *         -> log.error("Error", e)));
     * </code>
     *
     * @param log
     * @return Mono empty
     */
    public static Mono<Void> log(Runnable log) {
        return Mono.empty()
                .doOnEach(ReactiveLogger.logOnComplete(log))
                .then();
    }

}
