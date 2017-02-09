package org.yashpalmeena.restclient;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.yashpalmeena.properties.Config;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Yashpal Meena <yaxhpal@gmail.com>
 *
 */
public class AvantiAPIClient {

	private final OkHttpClient client = new OkHttpClient();


	public AvantiAPIClient() {}

	/**
	 * Get the access token from Auth Server using given credentials
	 * @param username Username to be used
	 * @param password User's password
	 * @return Access token
	 * @throws Exception If any
	 */
	public String getAccessToken(String username, String password) throws Exception {
		RequestBody formBody = new FormBody.Builder()
				.add(Config.GRANT_TYPE, Config.get(Config.GRANT_TYPE))
				.add(Config.USERNAME, username)
				.add(Config.PASSWORD, password)
				.build();

		Request request = new Request.Builder()
				.url(Config.get(Config.TOKEN_URL))
				.post(formBody)
				.build();

		Response response = client.newCall(request).execute();

		if (!response.isSuccessful()) {
			throw new IOException("Unexpected code " + response);
		}
		JSONObject jsonResponse = new JSONObject(response.body().string());
		Config.set(Config.TOKEN_NAME, jsonResponse.getString(Config.TOKEN_NAME));
		return Config.get(Config.TOKEN_NAME);
	}

	public void getCentres() throws Exception {
		JSONArray centres = getResource(Config.get("lms_centres_url"));
		System.out.println(centres.toString());
	}

	private JSONArray getResource(String resourceUrl) throws Exception {
		String access_token = Config.get(Config.TOKEN_NAME);
		if (access_token == null || access_token.isEmpty()) {
			access_token = getAccessToken(Config.get("user.username"), Config.get("user.password"));
		}
		Request request = new Request.Builder()
				.url(resourceUrl)
				.header(Config.get("auth_header_name"), Config.get("auth_header_value", access_token))
				.build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		
		return new JSONArray(response.body().string());		
	}
}
