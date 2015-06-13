import java.util.Calendar;

public class Movie {
    private String title;
    private String genre;
    private Review[] reviews;
    private Calendar releaseDate;
    private String synopsis;
    
    public Movie(String title, String genre, Review[] reviews, Calendar releaseDate, String synopsis) {
        this.title = title;
        this.genre = genre;
        this.reviews = reviews;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
    }
    
    public int avgScore() {
        return 0;
    }
}
