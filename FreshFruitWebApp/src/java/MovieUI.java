import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean (name = "movieui")
@SessionScoped
public class MovieUI extends UI {
    private Movie[] movies;
    
    private String query;
    
    private Movie[] newReleases;
    
    private Movie[] newDvd;
    
    private MovieLogic movieLogic;
   
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
            movies = MovieLogic.findMovies(query.trim());
        }
        return "search";
    }
    
    /**
     * Gets the newest movie releases.
     * @return the search screen
     */
    public String releases() {
        movies =  MovieLogic.getNewMovies();
        return "searchNewReleases";
    }
    /**
     * get new releases movies
     * @return 
     */
    public Movie[] getNewReleases() {
        return newReleases;
    }
    /**
     * Gets the newest DVD releases.
     * @return the search screen
     */
    public String dvds() {
        movies = movieLogic.getNewDvd();        
        return "searchNewDvd";
    }
    
    /**
     * get new dvds
     * @return 
     */
    public Movie[] getNewDvd() {
        return newDvd;
    }
}   

