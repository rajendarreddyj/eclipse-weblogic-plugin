package com.rajendarreddyj.eclipse.plugins.weblogic;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RemoteControl implements WeblogicPluginResources {
    private static HttpServer server = null;

    public static void init(final String startUrl, final String stopUrl, final Integer port) throws Exception {
        if (server != null) {
            return;
        }
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/" + startUrl, new HttpHandler() {
            @Override
            public void handle(final HttpExchange t) throws IOException {
                String response = "START COMMAND SENT - SUCCESS";
                try {
                    WeblogicPlugin.getDefault().startWeblogic().run();
                } catch (Exception e) {
                    WeblogicPlugin.log(WEBLOGIC_REMOTE_START_MSG);
                    WeblogicPlugin.log(e);
                    response = "START COMMAND SENDING - FAIL";
                }
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        server.createContext("/" + stopUrl, new HttpHandler() {
            @Override
            public void handle(final HttpExchange t) throws IOException {
                String response = "STOP COMMAND SENT - SUCCESS";
                try {
                    WeblogicPlugin.getDefault().stopWeblogic().run();
                } catch (Exception e) {
                    WeblogicPlugin.log(WEBLOGIC_REMOTE_STOP_MSG);
                    WeblogicPlugin.log(e);
                    response = "STOP COMMAND SENDING - FAIL";
                }
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });
        server.setExecutor(null);
        server.start();
    }

    public static void shutown() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }
}