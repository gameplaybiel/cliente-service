package com.tcc.cliente_service.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pedido.service")
public class ServiceProperties {
    private String pedidosUrl;

    // getters e setters
    public String getPedidosUrl() {
        return pedidosUrl;
    }

    public void setPedidosUrl(String pedidosUrl) {
        this.pedidosUrl = pedidosUrl;
    }
}
