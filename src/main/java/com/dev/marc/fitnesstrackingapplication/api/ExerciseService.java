package com.dev.marc.fitnesstrackingapplication.api;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseService {
	private static final String API_URL = "https://api.api-ninjas.com/v1/exercises";
	private static final String API_KEY = "sWUm3bS6h0MHFVkWiNQbdA==WIcUHNQW0rUIfAnD"; // Replace with your API key

	public static List<String> getExercises(String muscleGroup) {
		List<String> exerciseList = new ArrayList<>();
		String url = API_URL + "?muscle=" + muscleGroup;

		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet request = new HttpGet(url);
			request.addHeader("X-Api-Key", API_KEY);

			try (CloseableHttpResponse response = client.execute(request)) {
				String jsonResponse = EntityUtils.toString(response.getEntity());

				// Debugging: Print API response
				System.out.println("API Response for " + muscleGroup + ": " + jsonResponse);

				JSONArray exercises = new JSONArray(jsonResponse);

				if (exercises.length() == 0) {
					exerciseList.add("No exercises found for " + muscleGroup);
				} else {
					for (int i = 0; i < exercises.length(); i++) {
						JSONObject exercise = exercises.getJSONObject(i);
						exerciseList.add(exercise.getString("name"));
					}
				}
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return exerciseList;
	}
}
