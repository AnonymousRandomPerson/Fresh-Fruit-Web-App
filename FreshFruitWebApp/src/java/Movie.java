import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Movie {
    private String title;
    private String genre;
    private List<Review> reviews;
    private Calendar releaseDate;
    private String synopsis;
    private String imagePath;
    private int id;
    
    public Movie(String title, String imagePath, int id) {
        this.title = title;
        this.imagePath = imagePath;
        genre = "Something";
        reviews = new ArrayList<>();
        releaseDate = Calendar.getInstance();
        releaseDate.set(2000, Calendar.JANUARY, 1);
        synopsis = "Something else";
        this.id = id;
    }
    
    public Movie(String title, String genre, ArrayList<Review> reviews, Calendar releaseDate, String synopsis, int id) {
        this.title = title;
        this.genre = genre;
        this.reviews = reviews;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    /**
     * Returns the movie's title.
     * @return the movie's title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Returns the URL of the image representing the movie.
     * @return the image path of the movie's image
     */
    public String getImagePath() {
        return imagePath;
    }
    
    /**
     * Returns the movie's genre.
     * @return the movie's genre
     */
    public String getGenre() {
        return genre;
    }
    
    /**
     * Returns the movie's release date.
     * @return the movie's release date
     */
    public String getReleaseDate() {
        return releaseDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " " + 
                releaseDate.get(Calendar.DAY_OF_MONTH) + ", " +
                releaseDate.get(Calendar.YEAR);
    }
    
    /**
     * Returns the movie's reviews, limited to a certain number.
     * @return a List of movie reviews
     */
    public List<Review> getReviews() {
        return reviews.size() < MovieLogic.limit ? reviews : reviews.subList(0, MovieLogic.limit);
    }
    
    /**
     * Returns the movie's synopsis.
     * @return the movie's synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }
    
    /**
     * Returns the movie's average rating.
     * @return the movie's average rating
     */
    public int avgScore() {
        int total = 0;
        for (Review review : reviews) {
            total += review.getStarRating();
        }
        total /= reviews.size();
        return total;
    }
    
    /**
     * Adds a review to the movie.
     * @param review the review to add
     */
    public void addReview(Review review) {
        reviews.add(review);
    }
}
