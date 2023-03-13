package com.tp.filereader.rest.api;

import com.tp.filereader.ServerVerticle;
import com.tp.filereader.entity.Data;
import com.tp.filereader.rest.common.Response;
import com.tp.filereader.rest.writers.MyResponseWriter;
import com.tp.filereader.service.DataService;
import com.zandero.rest.annotation.ResponseWriter;
import io.swagger.annotations.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Api(value = ServerVerticle.API_ROOT, tags = "phase 1 - data")
@Path(ServerVerticle.API_ROOT + "/data")
@Produces(MediaType.APPLICATION_JSON)
@ResponseWriter(MyResponseWriter.class)
public class DataRest extends BaseRest {

	private static final Logger LOG = LoggerFactory.getLogger(DataRest.class);

	@Inject
	private DataService dataService;

	@ApiOperation(value = "API-0101 | Return list of popular artwork", notes = "pageNumber default value is 1 ", response = Data.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return Data list is OK."), @ApiResponse(code = 400, message = "Failed to return the data list.") })
	@GET
	@Path("/")
	public Response<JsonObject> getPopularArtwork(@Context RoutingContext context) {
		handle(context);
		final JsonObject resultJson = new JsonObject();
		return Response.ok(resultJson);
	}

}
