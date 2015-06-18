import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name = "movieui")
@SessionScoped
public class MovieUI extends UI {
    private Movie[] movies;
    
    private String query;
    
    private List<Movie> newReleases = new ArrayList<Movie>();
    
    public MovieUI() {
    }
    
    /**
     * Returns the user's search query.
     * @return the user's search query
     */
    public String getQuery() {
        return query;
    }
    
    public Movie[] getMovies() {
        return movies;
    }
    
    /**
     * Sets the user's search query.
     * @param query the new search query
     */
    public void setQuery(String query) {
        this.query = query;
    }
    
    /**
     * Gets the movies that match the user's search query.
     * @return the search screen
     */
    public String search() {
        if (query.equalsIgnoreCase("New Releases")) {
            releases();
        } else if (query.equalsIgnoreCase("New DVDs")) {
            dvds();
        } else {
            movies = new Movie[] {new Movie("Toy Story 2", "http://resizing.flixster.com/22gW_78MCw3LuSGJUBYmVIssDUo=/54x77/dkpu1ddg7pbsk.cloudfront.net/movie/10/93/63/10936392_ori.jpg"),
                new Movie("Toy Story 3", "http://content6.flixster.com/movie/11/13/43/11134356_tmb.jpg")};
        }
        return "search";
    }
    
    /**
     * get Json data from the API
     * @param link 
     * @return the json data
     */
    public String getJsonData(String link) {
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
    /**
     * Gets the newest movie releases.
     * @return the search screen
     */
    public String releases() {
        query = "New Releases";
        Gson gson = new Gson();
        String jsonData = getJsonData("http://api.rottentamatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=yedukp76ffytfuy24zsqk7f5?limit=20?country=us");
        
        return "search";
    }
    public List<Movie> getNewReleases() {
        return newReleases;
    }
    /**
     * Gets the newest DVD releases.
     * @return the search screen
     */
    public String dvds() {
        query = "New DVDs";
        return "search";
    }
}