package Objects;

import java.io.Serializable;

public class Review implements Serializable {

    private int rating;

    private Buyer buyer;

    private Seller seller;

    private String title;

    private String description;

    public Review(int rating, Buyer buyer, Seller seller, String title, String description) {
        this.rating = rating;
        this.buyer = buyer;
        this.seller = seller;
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

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
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
        return "Review{" + "rating=" + rating + ", buyer=" + buyer + ", seller=" + seller + ", title='" + title + '\'' + ", description='" + description + '\'' + '}';
    }
}
