import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A movie and its details.
 */
public class Movie {
    private String title;
    private List<Review> reviews;
    private String releaseDate;
    private String synopsis;
    private String imagePath;
    private int id;
    
    public Movie(String title, String imagePath, int id) {
        this.title = title;
        this.imagePath = imagePath;
        reviews = new ArrayList<>();
        releaseDate = "2000-01-01";
        synopsis = "Something else";
        this.id = id;
    }
    
    public Movie(String title, String imagePath, String releaseDate, String synopsis, int id) {
        this.title = title;
        this.imagePath = imagePath;
        reviews = new ArrayList<>();
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.id = id;
        
        try {
            Connection con = DriverManager.getConnection(MovieLogic.host, MovieLogic.uName, MovieLogic.uPass);
            Statement stmt = con.createStatement();
            String SQL = "SELECT STARRATING, TEXTREVIEW, USERID FROM REVIEWS WHERE MOVIEID='" + id + "'";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                StudentUser tempUser = (StudentUser)UserManager.find(rs.getInt("USERID"));
                reviews.add(new Review(Integer.parseInt(rs.getString("STARRATING")), rs.getString("TEXTREVIEW"), tempUser));
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
    
    /**
     * Returns the movie's id.
     * @return the movie's id
     */
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
     * Returns the movie's release date.
     * @return the movie's release date
     */
    public String getReleaseDate() {
        return releaseDate;
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
