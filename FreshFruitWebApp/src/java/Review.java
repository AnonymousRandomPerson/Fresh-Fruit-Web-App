public class Review {
    private int starRating;
    private String textReview;
    private StudentUser reviewer;
    
    public Review(int starRating, String textReview, StudentUser reviewer) {
        this.starRating = starRating;
        this.textReview = textReview;
        this.reviewer = reviewer;
    }
}
