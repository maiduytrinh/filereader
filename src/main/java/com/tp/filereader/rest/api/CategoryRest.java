package com.tp.filereader.rest.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import com.ringtone.token.Token;
import com.tp.filereader.ServerVerticle;
import com.tp.filereader.Utils;
import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Category;
import com.tp.filereader.rest.common.Response;
import com.tp.filereader.rest.common.ResponseBase;
import com.tp.filereader.rest.writers.MyResponseBaseWriter;
import com.tp.filereader.rest.writers.MyResponseWriter;
import com.tp.filereader.service.CategoryService;
import com.tp.filereader.utils.AuthUtils;
import com.zandero.rest.annotation.ResponseWriter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

@Api(value = ServerVerticle.API_ROOT, tags = "phase 1 - category")
@Path(ServerVerticle.API_ROOT + "/categories")
@Produces(MediaType.APPLICATION_JSON)
@ResponseWriter(MyResponseBaseWriter.class)
public class CategoryRest extends BaseRest {
	private static final Logger LOG = LoggerFactory.getLogger(DataRest.class);

	@Inject
	private CategoryService categoryService;

	@ApiOperation(value = "API-01 | Get list Category", responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return Category list is OK."),
			@ApiResponse(code = 400, message = "Failed to return the Category list.") })
	@GET
	@Path("/")
	public ResponseBase<List<Category>> getListCategory(
			@ApiParam(value = "The country value", required = true)@QueryParam("country") String country,
			@ApiParam(value = "The mobileid value", defaultValue = "167e5a47e70ee6c3_sdk29_tg28", required = true) @QueryParam("mobileid") String mobileid,
			@ApiParam(value = "The token value", defaultValue = "testtoken", required = true) @QueryParam("token") String token,
			@ApiParam(value = "The appid value", defaultValue = "testappid", required = true) @QueryParam("appid") String appId,
			@Context RoutingContext context) {

		handle(context);
		//		if (!"testAppID".equals(appId) && !Token.validateAppIdToken(mobileid, token, appId, "") && !AuthUtils.validateByFirebaseId(context, mobileid)) {
		//    		return Response.unauthorized();
		//    	}
		country = country.toUpperCase();
		//check country support
		List<String> countrySupport = Utils.supportedCountries;
		boolean isSupport = countrySupport.contains(country);
		if(isSupport) {
			//get list category
			ResultContext<List<Category>> cateList = categoryService.getListCategory(country);
			if (cateList.notFound()) {
				return ResponseBase.notFound("Cant get list category");
			}
			return ResponseBase.ok(cateList.result());
		}else {
			return ResponseBase.notFound("Cant get list category");
		}
	}

}
