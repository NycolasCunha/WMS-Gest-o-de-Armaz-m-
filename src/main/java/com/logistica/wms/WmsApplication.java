package com.logistica.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Ponto de entrada do WMS Joinville.
 *
 * <p>O agendamento é habilitado globalmente para suportar os alertas
 * automáticos (validade de lotes, estoque mínimo e vagas bloqueadas).</p>
 */
@SpringBootApplication
@EnableScheduling
public class WmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WmsApplication.class, args);
    }
}
