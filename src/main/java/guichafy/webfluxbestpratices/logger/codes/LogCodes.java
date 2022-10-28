package guichafy.webfluxbestpratices.logger.codes;

public enum LogCodes {

    E_10000("Erro generico");

    private final String message;


    LogCodes(String message) {
        this.message = message;
    }

    public String getCode(){
        return this.name();
    }

    public String getMessage() {
        return message;
    }
}
