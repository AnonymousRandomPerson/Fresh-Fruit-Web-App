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
    
    /**
     * The number of results to show.
     */
    public static final int limit = 50;
    
    /**
     * Finds movies based on a search query.
     * @param search the search query
     * @return an array of movies matching the search query
     */
    public static Movie[] searchMovies(String search) {
        String link = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yedukp76ffytfuy24zsqk7f5&q="
                    + search.replaceAll("\\s", "+") + "&page_limit=" + limit;
        return findMovies(link);
    }
    
    /**
     * Finds the latest DVD movie releases.
     * @return an array of new DVD releases
     */
    public static Movie[] getNewDvd(){
        String link = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?page_limit=" + limit + "&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        return findMovies(link);
    }
    
    /**
     * Finds recommended movies based on major.
     * @param major the major to search for recommended movies from
     * @return an array of recommended movies
     */
    public static Movie[] recommendMovies(Major major) {
        return new Movie[0];
    }
    
    /**
     * Finds the newest movie releases,
     * @return an array of new movies
     */
    public static Movie[] getNewMovies() {
        String link = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?page_limit=" + limit + "&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        return findMovies(link);
    }
    
    /**
     * Finds the top rated movies.
     * @return an array of top movies.
     */
    public static Movie[] getTopMovies() {
        String link = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/top_rentals.json?limit=" + limit + "&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        return findMovies(link);
    }
    
    public static Movie[] getSimilarMovies(int id) {
        String link = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + id + "/similar.json?limit=5&apikey=yedukp76ffytfuy24zsqk7f5";
        return findMovies(link);
    }
    /**
     * Gets movies from Rotten Tomatoes based on the query.
     * @param query the URL to get the JSON from
     * @return the movies matching the query
     */
    public static Movie[] findMovies(String query) {
        String callResult = getJsonData(query);
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(callResult);
        JsonArray jsonArr = jo.getAsJsonArray("movies");
        Gson googleJson = new Gson();
        ArrayList jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
        Pattern titlePattern = Pattern.compile("title=(.+), year");
        Pattern thumbnailPattern = Pattern.compile("thumbnail=(.+), profile");
        Pattern idPattern = Pattern.compile("id=(.+), title");
        int numMovies = Math.min(limit, jsonObjList.size());
        Movie[] movies = new Movie[numMovies];
        for (int i = 0; i < numMovies; i++) {
            Matcher titleMatch = titlePattern.matcher(jsonObjList.get(i).toString());
            titleMatch.find();
            Matcher thumbnailMatch = thumbnailPattern.matcher(jsonObjList.get(i).toString());
            thumbnailMatch.find();
            Matcher idMatch = idPattern.matcher(jsonObjList.get(i).toString());
            idMatch.find();
            movies[i] = new Movie(titleMatch.group(1), thumbnailMatch.group(1), Integer.parseInt(idMatch.group(1)));
        }
        return movies;
    }
    
    /**
     * Gets JSON data from the API
     * @param link the URL to get the JSON from
     * @return the JSON data
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
		//System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			//System.out.println(output);
                        jsonData+=output;
		}
                //System.out.println("Got JSON: " + jsonData);
		conn.disconnect();
                } catch (MalformedURLException ex) {
                   Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    System.out.println("Cannot open url");
                }
        return jsonData;
    }
}
