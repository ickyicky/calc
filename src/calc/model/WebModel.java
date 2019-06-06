package calc.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

public class WebModel extends Model {
	private String uri;

	public WebModel(String uri) {
		this.uri = uri;
	}

	public Double calculate(String equasion) throws FormatException {
		JSONObject json = new JSONObject();
		json.put("equasion", equasion);

		HttpResponse<String> response = makeRequest(json.toString());

		if (response.statusCode() != 200)
			throw (new FormatException("Ivalid format!"));

		JSONObject parsed = new JSONObject(response.body());
		return parsed.getDouble("result");
	}

	private HttpResponse<String> makeRequest(final String json) throws FormatException {

		HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_2) // this is the default
				.build();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString(json)).build();

		try {
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			return response;

		} catch (IOException | InterruptedException e) {
			throw (new FormatException("Connection failed!"));
		}
	}
}
