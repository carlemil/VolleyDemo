package se.kjellstrand.volleydemo;

/**
 * Created by erbsman on 7/25/13.
 */
public class DemoItem {
    /**
     * id: "8",
     * author: "Mark McGee",
     * price: 7.11,
     * image: "http://assignment.golgek.mobi/static/711.jpg",
     * title: "A Dance with Dragons",
     * link: "/api/v10/items/8"
     */
    private int id;
    private String title;
    private String link;
    private String author;
    private String price;
    private String image;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
