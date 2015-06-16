import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean (name = "movieui")
@SessionScoped
public class MovieUI extends UI {
    private Movie[] movies;
    
    private String query;
    
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
     * Gets the newest movie releases.
     * @return the search screen
     */
    public String releases() {
        query = "New Releases";
        return "search";
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