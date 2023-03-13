package com.tp.filereader.rest.api;


import static com.tp.filereader.ServerVerticle.API_ROOT;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.google.inject.Inject;
import com.tp.filereader.Utils;
import com.tp.filereader.index.SearchResult;
import com.tp.filereader.logger.TPLogger;
import com.tp.filereader.rest.common.Response;
import com.tp.filereader.rest.writers.MyResponseWriter;
import com.tp.filereader.service.SearchService;
import com.zandero.rest.annotation.ResponseWriter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

@Api(value = API_ROOT, tags = "phase 1 - search")
@Path(API_ROOT)
@Produces(MediaType.APPLICATION_JSON)
@ResponseWriter(MyResponseWriter.class)
public class SearchRest extends BaseRest {

	private static final Logger LOG = TPLogger.getLogger(SearchRest.class);

	@Inject
	private SearchService searchService;


	@ApiOperation(value = "API-10-03 | Fulltext search keyword ", responseContainer = "List")
	@ApiResponses(value = { @ApiResponse (code = 200, message = "Return Wallpaper list is OK."),
			@ApiResponse(code = 400, message = "Failed to return the Wallpaper list.")  })
	@GET
	@Path("/search")
	public Response<List<SearchResult>> searchKeyword(
			@ApiParam(value = "The country value", defaultValue = "US" ,required = true) @QueryParam("lang") String lang,
			@ApiParam(value = "The keyword value", defaultValue = "hello" ,required = true) @QueryParam("q") String keyword,
			@ApiParam(value = "The offset value", allowableValues = "range[0,infinity]", defaultValue= "0", required = true) @DefaultValue("0") @QueryParam("offset") int offset,
			@ApiParam(value = "The limit value", allowableValues = "range[0,infinity]",defaultValue = "100", required = true) @DefaultValue("100") @QueryParam("limit") int limit,
			@ApiParam(value = "The mobileid value", defaultValue = "167e5a47e70ee6c3_sdk29_tg28", required = true) @QueryParam("mobileid") String mobileid,
			@ApiParam(value = "The token value", defaultValue = "testtoken", required = true) @QueryParam("token") String token,
			@ApiParam(value = "The appid value", defaultValue = "testappid", required = true) @QueryParam("appid") String appId,
			@ApiParam(value = "The search type value", defaultValue = "keyword", required = true) @QueryParam("searchtype") String searchtype,
			@Context RoutingContext context) {

		LOG.info("=======================API:03 - Search=======================");
		String country = Utils.getCountryBySupported(lang);
		if (StringUtils.isEmpty(keyword) || StringUtils.isBlank(keyword)) {
			return Response.fail("Not found result in the search keyword");
		}
		String detectLang = Utils.langDetect(keyword);
		if ("vi".equalsIgnoreCase(detectLang) && Utils.supportedCountries.contains("VN")) {
			country = "VN";
		}
		String keywordInput = Utils.getQueryInput(keyword, false);
		LOG.info("searchKeyword: country = {}, keyword = {}, detectLang {}", country, keywordInput, detectLang);
		if (limit == 0) {
			limit = 100;
		}
		List<SearchResult> list = new ArrayList<>();
		if(searchtype.equals("hashtag")) {
			LOG.info("start search by hashtag with country {} and hashtag: {}",country,keyword);
			list = searchService.searchByHashtag(country, keywordInput, offset, limit);
		} else {
			LOG.info("start search by keyword with country {} and keyword: {}",country,keyword);
			list = searchService.searchKeyword(country, keywordInput, offset, limit);
		}
		if(list.isEmpty()) {
			return Response.fail("Not found result in the search keyword or search hashtag");
		}
		limit = limit == 0 ? 100 : limit;
		int nextOffset = offset + Math.min(list.size(), limit);
		return Response.ok(list).setHasnext(list.size() == limit).setNextoffset(nextOffset);
	}

	@ApiOperation(value = "API-10-05 | Rebuild indexing for keyword or search hashtag")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Number of deleted and inserted document is OK."),
			@ApiResponse(code = 400, message = "Failed to delete and insert document is OK.")  })
	@GET
	@Path("/reindex/keyword")
	public Response<JsonObject> rebuildKeyword() {

		LOG.info("API-10-05::rebuildKeyword()-----------------------------------------------------------------------------");
		JsonObject result = searchService.rebuildKeywordIndex();
		return Response.ok(result);
	}
}
