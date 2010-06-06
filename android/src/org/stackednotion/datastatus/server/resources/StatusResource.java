package org.stackednotion.datastatus.server.resources;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import org.stackednotion.datastatus.DataStatusApplication;

public class StatusResource extends ServerResource {
	@Get
	public Representation represent() {
		String json = "{\"dataConnectionType\":"
				+ String.valueOf(DataStatusApplication.signalStrengthListener
						.getDataConnectionType()) + "}";

		return new StringRepresentation(json);
	}
}
