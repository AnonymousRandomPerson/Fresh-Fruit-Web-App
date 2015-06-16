import java.util.Calendar;

public class Movie {
    private String title;
    private String genre;
    private Review[] reviews;
    private Calendar releaseDate;
    private String synopsis;
    private String imagePath;
    
    public Movie(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }
    
    public Movie(String title, String genre, Review[] reviews, Calendar releaseDate, String synopsis) {
        this.title = title;
        this.genre = genre;
        this.reviews = reviews;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
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
     * Returns the movie's average rating.
     * @return the movie's average rating
     */
    public int avgScore() {
        return 0;
    }
}
