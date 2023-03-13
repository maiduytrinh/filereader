package com.tp.filereader.rest.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tp.filereader.ServerVerticle;
import com.tp.filereader.Utils;
import com.tp.filereader.common.ResultContext;
import com.tp.filereader.entity.Documents;
import com.tp.filereader.rest.common.ResponseBase;
import com.tp.filereader.rest.common.Status;
import com.tp.filereader.rest.writers.MyResponseBaseWriter;
import com.tp.filereader.service.DocumentService;
import com.zandero.rest.annotation.BodyParam;
import com.zandero.rest.annotation.ResponseWriter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.vertx.ext.web.RoutingContext;

@Api(value = ServerVerticle.API_ROOT, tags = "phase 1 - document")
@Path(ServerVerticle.API_ROOT + "/documents")
@Produces(MediaType.APPLICATION_JSON)
@ResponseWriter(MyResponseBaseWriter.class)
public class DocumentRest extends BaseRest {
	private static final Logger LOG = LoggerFactory.getLogger(DataRest.class);

	@Inject
	private DocumentService documentService;

	@ApiOperation(value = "API-02 | Return list document of category", responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return Data list is OK."),
			@ApiResponse(code = 404, message = "Failed to return the data list.") })
	@GET
	@Path("/")
	public ResponseBase<List<Documents>> getListDocumentOfCategory(
			@ApiParam(value = "The id category value", required = true) @QueryParam("cateid") String cateId,
			@ApiParam(value = "The country value", required = true)@QueryParam("country") String country,
			@ApiParam(value = "The limit value", allowableValues = "range[0,infinity]", defaultValue = "100", required = true) @DefaultValue("100") @QueryParam("limit") int limit,
			@ApiParam(value = "The mobileid value", defaultValue = "167e5a47e70ee6c3_sdk29_tg28", required = true) @QueryParam("mobileid") String mobileid,
			@ApiParam(value = "The token value", defaultValue = "testtoken", required = true) @QueryParam("token") String token,
			@ApiParam(value = "The appid value", defaultValue = "testappid", required = true) @QueryParam("appid") String appId,
			@Context RoutingContext context) {

		handle(context);
		List<String> countrySupport = Utils.supportedCountries;
		boolean isSupport = countrySupport.contains(country.toUpperCase());
		if (isSupport) {
			ResultContext<List<Documents>> listDocuments = documentService.getListDocumentOfCategory(cateId, limit);
			if (listDocuments.notFound()) {
				return ResponseBase.notFound("Cant get list files");
			}
			for(Documents documents : listDocuments.result()) {
				documents.setCategories(documents.getCategories().substring(1, documents.getCategories().length() - 1));
			}
			return ResponseBase.ok(listDocuments.result());
		} else {
			return ResponseBase.notFound("Cant get list files");
		}
	}

	@ApiOperation(value = "API-06 | Record Action File", response = Status.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK."),
			@ApiResponse(code = 400, message = "unsuccess") })
	@POST
	@Path("/record-action")
	public ResponseBase<Documents> recordActionFile(
			@ApiParam(value = "The document value", required = true) @HeaderParam("id") String id,
			@ApiParam(value = "The lang value") @HeaderParam("lang") String lang,
			@ApiParam(value = "The type value", required = true) @HeaderParam("type") String type,
			@ApiParam(value = "The mobileid value", defaultValue = "167e5a47e70ee6c3_sdk29_tg28", required = true) @HeaderParam("mobileid") String mobileid,
			@ApiParam(value = "The token value", defaultValue = "testtoken", required = true) @HeaderParam("token") String token,
			@ApiParam(value = "The appid value", defaultValue = "testappid", required = true) @HeaderParam("appid") String appId,
			@Context RoutingContext context) {

		handle(context);
		type = type.toLowerCase();
		ResultContext<Documents> response = null;
		if(StringUtils.isEmpty(id)) {
			return ResponseBase.fail("unsuccess");
		}
		try {
			if (type.equals("down")) {
				response = documentService.recordDownFile(Integer.valueOf(id));
			} else if (type.equals("view")) {
				response = documentService.recordViewFile(Integer.valueOf(id));
			} else {
				return ResponseBase.fail("unsuccess");
			}
			if (!response.succeeded()) {
				return ResponseBase.fail("unsuccess");
			}
			return ResponseBase.ok();
		} catch(Exception  e) {
			LOG.info("Error record action file");
		}
		return ResponseBase.fail("unsuccess");
		
	}
}
