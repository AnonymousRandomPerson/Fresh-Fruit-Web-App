public class Review {
    private int starRating;
    private String textReview;
    private User reviewer;
    
    public Review(int starRating, String textReview, User reviewer) {
        this.starRating = starRating;
        this.textReview = textReview;
        this.reviewer = reviewer;
    }
}
