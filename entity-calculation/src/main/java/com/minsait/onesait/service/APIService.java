package com.minsait.onesait.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.onesait.pojo.FnEntityOutputWrapper;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIService {

	private final String apiKey;
	private final String backend;
	private final OkHttpClient client;
	private static final String API_PATH = "api-manager/server/api/v1/fnoutput";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LogManager.getLogger(APIService.class);

	public APIService(String apiKey, String backend) {
		this.apiKey = apiKey;
		this.backend = backend.endsWith("/") ? backend : backend + "/";
		this.client = new OkHttpClient();
	}

	public void createEntry(FnEntityOutputWrapper output) {
		try {
			final RequestBody body = RequestBody.create(JSON, mapper.writeValueAsString(output));
			final Request request = new Request.Builder().addHeader("Content-Type", "application/json")
					.addHeader("Accept", "application/json").addHeader("X-OP-APIKey", apiKey).url(backend + API_PATH)
					.post(body).build();
			final Call call = client.newCall(request);
			final Response response = call.execute();
			logger.info("Called API {} with status {}", backend + API_PATH, response.code());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
