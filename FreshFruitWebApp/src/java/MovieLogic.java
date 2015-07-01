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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Contains static business logic functions for movie searching.
 */
public class MovieLogic {
    
    /**
     * The number of results to show.
     */
    public static final int limit = 50;
    public static final String host = "jdbc:derby://localhost:1527/fruit";
    public static final String uName = "team11";
    public static final String uPass= "fruit";
    
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
        ArrayList ids = new ArrayList();
        ArrayList totals = new ArrayList();
        ArrayList reviewCount = new ArrayList();
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "SELECT MOVIEID,STARRATING FROM REVIEWS WHERE REVIEWMAJOR=\'" + major + "\'";
            ResultSet rs = stmt.executeQuery( SQL );
            while (rs.next()) {
                int testID = Integer.parseInt(rs.getString("MOVIEID"));
                if (ids.contains(testID)) {
                    totals.set(ids.indexOf(testID), Integer.parseInt(rs.getString("STARRATING")) + (int) totals.get(ids.indexOf(testID)));
                    reviewCount.set(ids.indexOf(testID), 1 + (int) reviewCount.get(ids.indexOf(testID)));
                } else {
                    ids.add(testID);
                    totals.add(Integer.parseInt(rs.getString("STARRATING")));
                    reviewCount.add(1);
                }
            }
            int[][] averages = new int[ids.size()][2];
            for (int i = 0; i < ids.size(); i++) {
                averages[i][0] = (int) totals.get(i) / (int) reviewCount.get(i);
                averages[i][1] = (int) ids.get(i);
            }
            java.util.Arrays.sort(averages, new java.util.Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                return Double.compare(b[0], a[0]);
                }
            });
            Movie[] movies = new Movie[ids.size()];
            for (int i = 0; i < ids.size(); i++) {
                Movie movie = getMovieById(averages[i][1]);
                if (movie != null) {
                    movies[i] = movie;
                }
            }
            return movies;
        }
        catch (SQLException err) {
            //err.printStackTrace();
        }
        return null;
    }
    
    /**
     * Takes an id number and returns the movie object
     * @return the movie corresponding to the number
     */
    public static Movie getMovieById(int id) {
        String link = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + id + ".json?apikey=yedukp76ffytfuy24zsqk7f5";
        String callResult = getJsonData(link);
        Pattern titlePattern = Pattern.compile("\"title\":\"(.+)\",\"year");
        Pattern thumbnailPattern = Pattern.compile("\"thumbnail\":\"(.+)\",\"profile");
        Pattern synopsisPattern = Pattern.compile("\"synopsis\":\"(.+)\",\"posters");
        Pattern releasePattern = Pattern.compile("\"theater\":\"(.+)\"},\"ratings");
        Matcher titleMatch = titlePattern.matcher(callResult);
        titleMatch.find();
        Matcher thumbnailMatch = thumbnailPattern.matcher(callResult);
        thumbnailMatch.find();
        Matcher synopsisMatch = synopsisPattern.matcher(callResult);
        synopsisMatch.find();
        Matcher releaseMatch = releasePattern.matcher(callResult);
        releaseMatch.find();
        try {
            return new Movie(titleMatch.group(1), thumbnailMatch.group(1), "IDK", synopsisMatch.group(1), id);
        } catch (IllegalStateException e) {
            //e.printStackTrace();
            return null;
        }
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
     * @return an array of top movies
     */
    public static Movie[] getTopMovies() {
        String link = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/top_rentals.json?limit=" + limit + "&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        return findMovies(link);
    }
    
    /**
     * Find movies similar to the specified movie.
     * @param id the id of the movie to search for similar movies with
     * @return an array of similar movies
     */
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
        Pattern synopsisPattern = Pattern.compile("synopsis=(.+), posters");
        Pattern releasePattern = Pattern.compile("theater=(.+)}, ratings");
        int numMovies = Math.min(limit, jsonObjList.size());
        Movie[] movies = new Movie[numMovies];
        for (int i = 0; i < numMovies; i++) {
            Matcher titleMatch = titlePattern.matcher(jsonObjList.get(i).toString());
            titleMatch.find();
            Matcher thumbnailMatch = thumbnailPattern.matcher(jsonObjList.get(i).toString());
            thumbnailMatch.find();
            Matcher idMatch = idPattern.matcher(jsonObjList.get(i).toString());
            idMatch.find();
            Matcher synopsisMatch = synopsisPattern.matcher(jsonObjList.get(i).toString());
            synopsisMatch.find();
            Matcher releaseMatch = releasePattern.matcher(jsonObjList.get(i).toString());
            releaseMatch.find();
            try {
                movies[i] = new Movie(titleMatch.group(1), thumbnailMatch.group(1), "IDK", synopsisMatch.group(1), Integer.parseInt(idMatch.group(1)));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
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
