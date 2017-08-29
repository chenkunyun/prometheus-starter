package com.kchen.prometheus.starter.jetty;

import io.prometheus.client.exporter.MetricsServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

/**
 * rpc jetty server
 * @author xuxueli 2015-11-19 22:29:03
 */
public class JettyServer {

	private Server server;
	private Thread thread;

	public void start(String host, Integer port, String contextPath, String path) throws Exception {

		thread = new Thread(new Runnable() {
			@Override
			public void run() {

				// The Server
				server = new Server(new ExecutorThreadPool());  // 非阻塞

				// HTTP connector
				ServerConnector connector = new ServerConnector(server);
				connector.setHost(host);
				connector.setPort(port);
				server.setConnectors(new Connector[]{connector});

				ServletContextHandler context = new ServletContextHandler();
				context.setContextPath(contextPath);
				server.setHandler(context);
				context.addServlet(new ServletHolder(new MetricsServlet()), path);
				try {
					// Start the server
					server.start();
					server.join();	// block until thread stopped
				} catch (Exception e) {
				} finally {
					destroy();
				}
			}
		});
		thread.setDaemon(true);	// daemon, service jvm, user thread leave >>> daemon leave >>> jvm leave
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
