import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean (name = "movieui")
@SessionScoped
public class MovieUI extends UI {
    private Movie[] movies;
    
    private String query;
    
    private Movie movie;
    
    private int rating;
    
    private String reviewText;
    
    private int id;
   
    public MovieUI() {
    }
    
    /**
     * Returns the user's search query.
     * @return the user's search query
     */
    public String getQuery() {
        return query;
    }
    
    /**
     * Gets movies currently stored in the UI.
     * @return an array of movies in the UI
     */
    public Movie[] getMovies() {
        return movies;
    }
    
    /**
     * Gets the current movie being looked at.
     * @return the current movie
     */
    public Movie getMovie() {
        return movie;
    }
    
    /**
     * Returns the text in the review box.
     * @return the text in the review box
     */
    public String getReviewText() {
        return reviewText;
    }
    
    /**
     * Sets the user's search query.
     * @param query the new search query
     */
    public void setQuery(String query) {
        this.query = query;
    }
    
    /**
     * Sets the review text in the UI text box.
     * @param reviewText the new review text
     */
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    
    /**
     * Sets the star rating in the UI.
     * @param rating the new rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    /**
     * Gets the movies that match the user's search query.
     * @return the search screen
     */
    public String search() {
        try {
            if (query.equalsIgnoreCase("New Releases")) {
                releases();
            } else if (query.equalsIgnoreCase("New DVDs")) {
                dvds();
            } else if (query.equalsIgnoreCase("Top Rated")) {
                top();
            } else if (query.equalsIgnoreCase("Recommended Movies")) {
                recommended();
            } else {
                movies = MovieLogic.searchMovies(query.trim());
            }
        } catch (NullPointerException e) {
            query = "";
            return "home";
        }
        return "search";
    }
    
    /**
     * Gets the newest movie releases.
     * @return the search screen
     */
    public String releases() {
        movies =  MovieLogic.getNewMovies();
        query = "New Releases";
        return "search";
    }
    
    /**
     * Gets the newest DVD releases.
     * @return the search screen
     */
    public String dvds() {
        movies = MovieLogic.getNewDvd(); 
        query = "New DVDs";       
        return "search";
    }
    
    /**
     * Gets the top rated movies.
     * @return an array of the top rated movies
     */
    public String top() {
        movies = MovieLogic.getTopMovies();
        query = "Top Rated";
        return "search";
    }
    
    public String similar(Movie mo) {
        int id = mo.getId();
        movies = MovieLogic.getSimilarMovies(id);
        return "search";
    }
    /**
     * Gets the recommended movies for the user's major.
     * @return an array of recommended movies
     */
    public String recommended() {
        StudentUser user = (StudentUser)userManager.getUser();
        movies = MovieLogic.recommendMovies(user.getMajor());
        query = "Recommended Movies";
        return "search";
    }
    
    /**
     * Navigates to movie details page.
     * @param movie the movie to display details for
     * @return the movie screen
     */
    public String movieDetails(Movie movie) {
        this.movie = movie;
        rating = 5;
        reviewText = "";
        return "movie";
    }
    
    /**
     * Adds the user's review to the movie.
     * @return null
     */
    public String rate() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (reviewText.equals("")) {
            context.addMessage(null, new FacesMessage("You need to enter a review before it can be submitted."));
            return null;
        }
        StudentUser user = (StudentUser)userManager.getUser();
        if (movie != null) {
            movie.addReview(new Review(rating, reviewText, user));
            rating = 5;
            reviewText = "";
            context.addMessage(null, new FacesMessage("Your review has been submitted."));
        }
        return "home";
    }
    
    /**
     * Decides whether the star to display is empty or filled.
     * @param position the star's position on the UI
     * @return the path of the star image
     */
    public String starImage(int position) {
        return rating < position ? "StarEmpty.png" : "StarFilled.png";
    }
}   

