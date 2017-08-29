package com.kchen.prometheus.starter.autoconfigure;

import com.kchen.prometheus.starter.exporter.MetricsExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PrometheusProperties.class)
@ConditionalOnMissingBean(MetricsExporter.class)
@ConditionalOnProperty
        (
                prefix = "prometheus",
                value = "enabled",
                matchIfMissing = true
        )
public class PrometheusAutoConfiguration {

    @Autowired
    private PrometheusProperties prometheusProperties;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MetricsExporter metricsExporter() {
        MetricsExporter exporter = new MetricsExporter();
        exporter.setHost(prometheusProperties.getHost());
        exporter.setPort(prometheusProperties.getPort());
        exporter.setContextPath(prometheusProperties.getContextPath());
        exporter.setPath(prometheusProperties.getPath());
        return exporter;
    }
}
