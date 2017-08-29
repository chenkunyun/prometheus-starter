package com.kchen.prometheus.starter.jetty;

import io.prometheus.client.exporter.MetricsServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

public class JettyServer {

	private Server server;
	private Thread thread;

	public void start(String host, Integer port, String contextPath, String path) throws Exception {

		thread = new Thread(() -> {
            server = new Server(new ExecutorThreadPool());  // 非阻塞

            ServerConnector connector = new ServerConnector(server);
            connector.setHost(host);
            connector.setPort(port);
            server.setConnectors(new Connector[]{connector});

            ServletContextHandler context = new ServletContextHandler();
            context.setContextPath(contextPath);
            server.setHandler(context);
            context.addServlet(new ServletHolder(new MetricsServlet()), path);
            try {
                server.start();
                server.join();
            } catch (Exception e) {
            } finally {
                destroy();
            }
        });
		thread.setDaemon(true);
		thread.start();
	}

	public void destroy() {
		if (server != null) {
			try {
				server.stop();
				server.destroy();
			} catch (Exception e) {
			}
		}
		if (thread.isAlive()) {
			thread.interrupt();
		}
	}
}
