package guichafy.webfluxbestpratices;

import guichafy.webfluxbestpratices.logger.codes.LogCodes;
import lombok.CustomLog;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@CustomLog
public class AppConfig {

    @PostConstruct
    public void init(){
        log.doLog("Logando Info no Init", LogCodes.I_10000);
        log.doLog("Logando Warn no Init", LogCodes.W_10000);
        log.doLog("Logando Error no Init", LogCodes.E_10000);
    }

}
