package guichafy.webfluxbestpratices.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import guichafy.webfluxbestpratices.filter.impl.ErrorFilter;
import guichafy.webfluxbestpratices.filter.impl.UserAgentFilter;
import guichafy.webfluxbestpratices.logger.LogCode;
import lombok.CustomLog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

@Configuration
@CustomLog
public class FilterConfig {


    @Bean
    @Order(-1)
    public ErrorFilter errorFilter(){
        return new ErrorFilter(new ObjectMapper());
    }

    @Bean
    @Order(10)
    public UserAgentFilter userAgentDetection(){
        return new UserAgentFilter();
    }

    @PostConstruct
    public void init(){
        log.doLog(LogCode.I_00001);
    }
}
