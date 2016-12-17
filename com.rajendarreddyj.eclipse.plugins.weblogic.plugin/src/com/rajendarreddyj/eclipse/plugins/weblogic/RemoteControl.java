package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RemoteControl implements WeblogicPluginResources {
	private static HttpServer server = null;

	public static void init(String startUrl, String stopUrl, Integer port) throws Exception {
		server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/" + startUrl, new HttpHandler() {
			@Override
			public void handle(HttpExchange t) throws IOException {
				String response = "START - SUCCESS";
				try {
					ILaunchConfiguration lc = DebugPlugin.getDefault().getLaunchManager()
							.getLaunchConfiguration("com.rajendarreddyj.eclipse.plugins.weblogic.toolbars.stopWeblogicCommand");
					lc.launch(ILaunchManager.DEBUG_MODE, null);
				} catch (Exception e) {
					response = e.getMessage();
				}
				t.sendResponseHeaders(200, response.length());
				OutputStream os = t.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
		});
		server.createContext("/" + stopUrl, new HttpHandler() {
			@Override
			public void handle(HttpExchange t) throws IOException {
				String response = "STOP - SUCCESS";
				try {
					ILaunchConfiguration lc = DebugPlugin.getDefault().getLaunchManager()
							.getLaunchConfiguration("com.rajendarreddyj.eclipse.plugins.weblogic.toolbars.startWeblogicCommand");
					lc.launch(ILaunchManager.DEBUG_MODE, null);
				} catch (Exception e) {
					response = e.getMessage();
				}
				t.sendResponseHeaders(200, response.length());
				OutputStream os = t.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
		});
		server.setExecutor(null); // creates a default executor
		server.start();
	}
	public static void shutown() {
		server.stop(0);
	}
}