package guichafy.webfluxbestpratices.context;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestContext {

    /**
     * Context map with all the context properties.
     * Property values must be {@link String} due to a limitation of MDC (Mapped Diagnostic Context).
     */
    private Map<String, Object> contextMap;

    /**
     * Constructor.
     */
    public RequestContext() {
        this.contextMap = new HashMap<>();
    }

    /**
     * Set the transaction id in the {@link #contextMap}.
     *
     * @param transactionId
     * @return {@link RequestContext}
     */
    public RequestContext setTransactionId(String transactionId) {
        return put(ContextField.TRANSACTION_ID, transactionId);
    }

    /**
     * Get the transaction id.
     *
     * @return transaction id
     */
    public String getTransactionId() {
        return getString(ContextField.TRANSACTION_ID);
    }

    /**
     * Set the correlator in the {@link #contextMap}.
     *
     * @param correlator
     * @return  {@link RequestContext}
     */
    public RequestContext setCorrelator(String correlator) {
        return put(ContextField.CORRELATOR, correlator);
    }

    /**
     * Get the correlator.
     *
     * @return correlator
     */
    public String getCorrelator() {
        return getString(ContextField.CORRELATOR);
    }

    /**
     * Set the operation in the {@link #contextMap}.
     *
     * @param operation
     * @return {@link RequestContext}
     */
    public RequestContext setOperation(String operation) {
        return put(ContextField.OPERATION, operation);
    }

    /**
     * Get the operation.
     *
     * @return operation
     */
    public String getOperation() {
        return getString(ContextField.OPERATION);
    }

    /**
     * Set the service in the {@link #contextMap}.
     *
     * @param service
     * @return {@link RequestContext}
     */
    public RequestContext setService(String service) {
        return put(ContextField.SERVICE, service);
    }

    /**
     * Get the service.
     *
     * @return service
     */
    public String getService() {
        return getString(ContextField.SERVICE);
    }

    /**
     * Set the component in the {@link #contextMap}.
     *
     * @param component
     * @return {@link RequestContext}
     */
    public RequestContext setComponent(String component) {
        return put(ContextField.COMPONENT, component);
    }

    /**
     * Get the component.
     *
     * @return component
     */
    public String getComponent() {
        return getString(ContextField.COMPONENT);
    }

    /**
     * Set the user in the {@link #contextMap}.
     *
     * @param user
     * @return {@link RequestContext}
     */
    public RequestContext setUser(String user) {
        return put(ContextField.USER, user);
    }

    /**
     * Get the user.
     *
     * @return user
     */
    public String getUser() {
        return getString(ContextField.USER);
    }

    /**
     * Set the realm in the {@link #contextMap}.
     *
     * @param realm
     * @return {@link RequestContext}
     */
    public RequestContext setRealm(String realm) {
        return put(ContextField.REALM, realm);
    }

    /**
     * Get the realm.
     *
     * @return realm
     */
    public String getRealm() {
        return getString(ContextField.REALM);
    }

    /**
     * Set a context property directly in the {@link #contextMap}.
     *
     * @param key
     * @param value
     * @return {@link RequestContext}
     */
    public RequestContext put(String key, String value) {
        contextMap.put(key, value);
        return this;
    }

    /**
     * Get a context property directly from the {@link #contextMap}.
     *
     * @param key
     * @return Value of the context property as {@link String}
     */
    public String getString(String key) {
        return (String) contextMap.get(key);
    }

    /**
     * Set a {@link Long} context property directly in the {@link #contextMap}.
     * The value is stored in the map as a {@link String} due to MDC limitations.
     *
     * @param key
     * @param value
     * @return {@link RequestContext}
     */
    public RequestContext put(String key, Long value) {
        String strValue = (value == null) ? null : Long.toString(value);
        return put(key, strValue);
    }

    /**
     * Get a context property directly from the {@link #contextMap} converting it to {@link Long}.
     *
     * @param key
     * @return Value of the context property as {@link Long}
     */
    public Long getLong(String key) {
        try {
            return Long.valueOf(getString(key));
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    /**
     * Set a {@link Boolean} context property directly in the {@link #contextMap}.
     * The value is stored in the map as a {@link String} due to MDC limitations.
     *
     * @param key
     * @param value
     * @return {@link RequestContext}
     */
    public RequestContext put(String key, Boolean value) {
        String strValue = (value == null) ? null : Boolean.toString(value);
        return put(key, strValue);
    }

    /**
     * Get a context property directly from the {@link #contextMap} converting it to {@link Boolean}.
     *
     * @param key
     * @return Value of the context property as {@link Boolean}.
     */
    public Boolean getBoolean(String key) {
        return Boolean.valueOf(getString(key));

    }

    /**
     * Retrieve the whole {@link #contextMap}.
     *
     * @return {@link Map} with all the context properties.
     */
    public Map<String, String> getContextMap() {
        return this.contextMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
    }


    /**
     * Retrieve the context from the reactive stream.
     *
     * @return Reactive {@link RequestContext} from reactor context.
     */
    public static Mono<RequestContext> context() {
        return Mono.deferContextual(Mono::just)
                .map(ctxt -> ctxt.getOrDefault(RequestContext.class, new RequestContext()));
    }

}
