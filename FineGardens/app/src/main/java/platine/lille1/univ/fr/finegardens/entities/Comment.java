package platine.lille1.univ.fr.finegardens.entities;

import java.util.Date;

/**
 * Created by cactus on 24/02/2018.
 */

public class Comment {
    private String jardinID;
    private String commentText;
    private String commentUser;
    private long commentTime;



    public Comment(String jardinID, String commentText, String commentUser ){
        this.jardinID = jardinID;

        this.commentText=commentText;
        this.commentUser=commentUser;

        commentTime = new Date().getTime();

    }
    public Comment(){

    }

    public String getCommentText() {
        return commentText;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }
    public void setJardinID(String jardinID) {
        this.jardinID = jardinID;
    }

    public String getJardinID() {

        return jardinID;
    }
    @Override
    public String toString() {
        return commentText;

    }
}