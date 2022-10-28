package guichafy.webfluxbestpratices;

import guichafy.webfluxbestpratices.logger.CustomLoggerFactory;
import guichafy.webfluxbestpratices.logger.LoggerWrapper;
import guichafy.webfluxbestpratices.logger.codes.LogCodes;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
//@Slf4j
public class AppConfig {


//    Logger log = LoggerFactory.getLogger(AppConfig.class);

    LoggerWrapper log = CustomLoggerFactory.getCustomLogger(AppConfig.class);


    @PostConstruct
    public void init(){
        log.doLog("Teste", LogCodes.E_10000);
//       log.info("Init");
    }

}
