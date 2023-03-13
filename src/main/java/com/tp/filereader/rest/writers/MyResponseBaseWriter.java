package com.tp.filereader.rest.writers;

import com.tp.filereader.rest.common.Response;
import com.tp.filereader.rest.common.ResponseBase;
import com.zandero.rest.writer.HttpResponseWriter;
import com.zandero.utils.extra.JsonUtils;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.jackson.DatabindCodec;

public final class MyResponseBaseWriter implements HttpResponseWriter<ResponseBase> {

	@Override
	public void write(ResponseBase result, HttpServerRequest request, HttpServerResponse response) throws Throwable {
		// TODO Auto-generated method stub
		if (result != null) {
			final String jsonResult = JsonUtils.toJson(result, DatabindCodec.mapper());
			response.setStatusCode(result.getStatus());
			response.putHeader("Accept-Encoding", "gzip");
			response.end(jsonResult);
		}
	}

}
