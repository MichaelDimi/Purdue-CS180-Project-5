package Objects;

import java.io.Serializable;

public class Review implements Serializable {

    private int rating;

    private Buyer buyer;

    private String sellerName;

    private String title;

    private String description;

    public Review(int rating, Buyer buyer, String sellerName, String title, String description) {
        this.rating = rating;
        this.buyer = buyer;
        this.sellerName = sellerName;
        this.title = title;
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return sellerName;
    }

    public void setSeller(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Review{" + "rating=" + rating + ", buyer=" + buyer + ", seller=" + sellerName + ", title='" + title + '\'' +
                ", description='" + description + '\'' + '}';
    }

    /**
     * Formats the ratings into a star display
     * @return The string star display
     */
    public String starDisplay(int amount) {
        String stars = "";
        switch (amount) {
            case 0:
                stars = "☆ ☆ ☆ ☆ ☆";
                break;
            case 1:
                stars = "★ ☆ ☆ ☆ ☆";
                break;
            case 2:
                stars = "★ ★ ☆ ☆ ☆";
                break;
            case 3:
                stars = "★ ★ ★ ☆ ☆";
                break;
            case 4:
                stars = "★ ★ ★ ★ ☆";
                break;
            case 5:
                stars = "★ ★ ★ ★ ★";
                break;
        }
        return stars;
    }

    /**
     * A version of toString() that is not for debugging, but for looking nice
     * @return returns a formatted string
     */
    public String print() {
        return String.format("%s\n%s says: %s\nDescription: %s\nOwner: %s", starDisplay(rating), buyer.getName(), title,
                description,
                sellerName);
    }
}
