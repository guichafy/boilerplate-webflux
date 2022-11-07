package guichafy.webfluxbestpratices.logger;

import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.event.Level;

public enum LogCode {

    I_10000("Info generico"),
    I_00001("Filters was initialized."),
    W_10000("Warn generico"),
    E_10000("Erro generico");

    private final String message;
    private final Level level;


    LogCode(String message) {
        this.level = getLevelByName(this.name());
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

    private Level getLevelByName(String name){
        if(name.startsWith("I")){
            return Level.INFO;
        } else if(name.startsWith("W")){
            return Level.WARN;
        } else if(name.startsWith("E")){
            return Level.ERROR;
        } else {
            return Level.WARN;
        }
    }
}
