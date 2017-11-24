package cz.srayayay.tierion.common;

import javax.ws.rs.core.Response;

public class Responses {

	public static void validateResponse(Response response, Response.Status expectedStatus) {
		if(!response.getStatusInfo().equals(expectedStatus)) {
			throw new TierionException(response.readEntity(ErrorResponse.class).getError());
		}
	}

}
