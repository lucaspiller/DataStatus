package org.stackednotion.datastatus.server;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.stackednotion.datastatus.Settings;
import org.stackednotion.datastatus.server.resources.StatusResource;

import android.util.Log;

public class ServerApplication extends Application {

	@Override
	public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/status", StatusResource.class);

		return router;
	}

	private static Component component;

	public static void startServer(int port) {
		try {
			component = new Component();
			component.getServers().add(Protocol.HTTP, port);
			component.getDefaultHost().attach(new ServerApplication());
			component.start();

			Log.d(Settings.LOG_TAG, "Server started at on port " + String.valueOf(port));
		} catch (Exception e) {
			Log.e(Settings.LOG_TAG, "Exception while starting server", e);
		}
	}

	public static void stopServer() {
		try {
			component.stop();

			Log.d(Settings.LOG_TAG, "Server stopped");
		} catch (Exception e) {
			Log.e(Settings.LOG_TAG, "Exception while stopping server", e);
		}
	}
}