package platine.lille1.univ.fr.finegardens.entities;

/**
 * Created by cactus on 04/03/2018.
 */

public class Note {
    private String jardinID;
    private float rating;
    private String userID;

    public Note(String jardinID, float rating, String userID) {

        this.jardinID = jardinID;
        this.rating = rating;
        this.userID = userID;
    }
    public Note(){

    }

    public void setJardinID(String jardinID) {
        this.jardinID = jardinID;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getJardinID() {

        return jardinID;
    }

    public float getRating() {
        return rating;
    }

    public String getUserID() {
        return userID;
    }


}
