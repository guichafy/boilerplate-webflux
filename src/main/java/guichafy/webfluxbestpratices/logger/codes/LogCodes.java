package guichafy.webfluxbestpratices.logger.codes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.event.Level;

public enum LogCodes {

    I_10000("Info generico"),
    W_10000("Warn generico"),
    E_10000("Erro generico");

    private final String message;
    private final Level level;


    LogCodes(String message) {
        if(this.name().startsWith("I")){
            this.level = Level.INFO;
        } else if(this.name().startsWith("W")){
            this.level = Level.WARN;
        } else if(this.name().startsWith("E")){
            this.level = Level.ERROR;
        } else {
            this.level = Level.WARN;
        }
        this.message = message;
    }

    public String getCode(){
        return this.name();
    }

    @JsonValue
    public String toLog(){
        return this.getCode() + " - " + this.getMessage();
    }

    public int getLevelCode(){
        return this.level.toInt();
    }

    public String getMessage() {
        return message;
    }
}
