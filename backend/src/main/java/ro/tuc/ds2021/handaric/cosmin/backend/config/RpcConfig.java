package ro.tuc.ds2021.handaric.cosmin.backend.config;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcConfig {

    @Bean
    public AutoJsonRpcServiceImplExporter getAutoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }
}
