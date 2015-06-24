import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class MovieLogic {
    
    public static Movie[] findMovies(String search) {
        
        int limit = 50; //The number of results to show
        String link = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yedukp76ffytfuy24zsqk7f5&q="
                    + search.replaceAll("\\s", "+") + "&page_limit=" + limit;
        String callResult = getJsonData(link);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(callResult);
        JsonArray jsonArr = jo.getAsJsonArray("movies");
        Gson googleJson = new Gson();
        ArrayList jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
        Movie[] movies = new Movie[limit];
        Pattern titlePattern = Pattern.compile("title=(.+), year");
        Pattern thumbnailPattern = Pattern.compile("thumbnail=(.+), profile");
        limit = Math.min(limit, jsonObjList.size());
        for (int i = 0; i < limit; i++) {
            Matcher titleMatch = titlePattern.matcher(jsonObjList.get(i).toString());
            titleMatch.find();
            Matcher thumbnailMatch = thumbnailPattern.matcher(jsonObjList.get(i).toString());
            thumbnailMatch.find();
            movies[i] = new Movie(titleMatch.group(1), thumbnailMatch.group(1));
        }
        return movies;
    }
    
    public static Movie[] getNewDvd(){
        int limit = 10;
        String link = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?page_limit=10&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        String callResult = getJsonData(link);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(callResult);
        JsonArray jsonArr = jo.getAsJsonArray("movies");
        Gson googleJson = new Gson();
        ArrayList jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
        Movie[] newDvd = new Movie[limit];
        Pattern titlePattern = Pattern.compile("title=(.+), year");
        Pattern thumbnailPattern = Pattern.compile("thumbnail=(.+), profile");
        limit = Math.min(limit, jsonObjList.size());
        for (int i = 0; i < limit; i++) {
            Matcher titleMatch = titlePattern.matcher(jsonObjList.get(i).toString());
            titleMatch.find();
            Matcher thumbnailMatch = thumbnailPattern.matcher(jsonObjList.get(i).toString());
            thumbnailMatch.find();
            newDvd[i] = new Movie(titleMatch.group(1), thumbnailMatch.group(1));
        }
        return newDvd;
    }
    
    public Movie[] recommendMovies() {
        return null;
    }
    
    public static Movie[] getNewMovies() {
        String query = "New Releases";
        int limit = 10;
        String link = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?page_limit=10&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        String callResult = getJsonData(link);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(callResult);
        JsonArray jsonArr = jo.getAsJsonArray("movies");
        Gson googleJson = new Gson();
        ArrayList jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
        Movie[] newReleases = new Movie[limit];
        Pattern titlePattern = Pattern.compile("title=(.+), year");
        Pattern thumbnailPattern = Pattern.compile("thumbnail=(.+), profile");
        limit = Math.min(limit, jsonObjList.size());
        for (int i = 0; i < limit; i++) {
            Matcher titleMatch = titlePattern.matcher(jsonObjList.get(i).toString());
            titleMatch.find();
            Matcher thumbnailMatch = thumbnailPattern.matcher(jsonObjList.get(i).toString());
            thumbnailMatch.find();
            newReleases[i] = new Movie(titleMatch.group(1), thumbnailMatch.group(1));
        }
        return newReleases;
    }
    
    public Movie[] getTopMovies() {
        return null;
    }
    
    /**
     * get Json data from the API
     * @param link 
     * @return the json data
     */
    public static String getJsonData(String link) {
        URL url = null;
        String jsonData = "";
        try {
            url = new URL(link);
        
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
                
                if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
                BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
                        jsonData+=output;
		}
                System.out.println("Got JSON: " + jsonData);
		conn.disconnect();
                } catch (MalformedURLException ex) {
                   Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    System.out.println("Cannot open url");
                }
        return jsonData;
    }
}
