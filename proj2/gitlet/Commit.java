package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import static gitlet.Repository.*;
import static gitlet.Utils.*;


// TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private String itshashcode;
    private Date date;

    private String parenthashcode;

    public Commit(String message,String parenthashcode){
        this.message = message;
        this.parenthashcode = parenthashcode;
        if (parenthashcode==null){
            date = new Date(0);
        }
        else{
            date = new Date();
        }
        getItshashcode();
    }

    public void getItshashcode(){
        this.itshashcode=sha1(message,date.toInstant(),parenthashcode);
    }

    public String getTimestamp() {
        // Thu Jan 1 00:00:00 1970 +0000
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public String getMessage(){
        return message;
    }

    public String getParenthashcode(){
        return parenthashcode;
    }

    public void savefile(){
        File commit = join (getCommitDir(),itshashcode);
        writeObject(commit,this);
    }

    /* TODO: fill in the rest of this class. */
}
