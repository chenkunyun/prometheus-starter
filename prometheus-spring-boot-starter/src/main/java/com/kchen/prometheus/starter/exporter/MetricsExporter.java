package com.kchen.prometheus.starter.exporter;

import com.kchen.prometheus.starter.jetty.JettyServer;

public class MetricsExporter {

    private String host;
    private Integer port = 9010;
    private String contextPath = "/";              // 上下文
    private String path = "/metrics";        // Prometheus路径

    private JettyServer jettyServer = new JettyServer();

    public MetricsExporter() {
    }

    public void start() {
        try {
            jettyServer.start(host, port, contextPath, path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        jettyServer.destroy();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
