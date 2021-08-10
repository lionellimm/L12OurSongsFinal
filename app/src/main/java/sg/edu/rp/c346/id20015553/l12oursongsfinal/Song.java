package sg.edu.rp.c346.id20015553.l12oursongsfinal;

import java.io.Serializable;

public class Song implements Serializable {

    private int id;
    private String title;
    private String singers;
    private int yearReleased;
    private float stars;

    public Song(String title, String singers, int yearReleased, float stars) {
        this.title = title;
        this.singers = singers;
        this.yearReleased = yearReleased;
        this.stars = stars;
    }

    public Song(int id, String title, String singers, int yearReleased, float stars) {
        this.id = id;
        this.title = title;
        this.singers = singers;
        this.yearReleased = yearReleased;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public Song setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Song setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSingers() {
        return singers;
    }

    public Song setSingers(String singers) {
        this.singers = singers;
        return this;
    }

    public int getYearReleased() {
        return yearReleased;
    }

    public Song setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
        return this;
    }

    public float getStars() {
        return stars;
    }

    public Song setStars(int stars) {
        this.stars = stars;
        return this;
    }

    @Override
    public String toString() {

        return "ID: " + id + "\nTitle: "+ title + "\nSingers: " + singers+ "\nYear: " + yearReleased + "\nStars: " + stars;

    }
}
