package com.kchen.prometheus.demo.config;

import com.kchen.prometheus.starter.exporter.MetricsExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfig {

    /*@Bean(initMethod = "start", destroyMethod = "stop")
    public MetricsExporter metricsExporter() {
        return new MetricsExporter();
    }*/
}
