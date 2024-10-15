package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import static gitlet.Repository.*;
import static gitlet.Repository.createnewFile;
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
    private List<String> parenthashcode;

    public Commit(){
        this.message = "have initcommit";
        date = new Date(0);
        parenthashcode = new ArrayList<>();
        itshashcode = getItshashcode();
    }
    public Commit(String message,Commit parent){
        this.message = message;
        parenthashcode = new ArrayList<>();
        parenthashcode.add(parent.getItshashcode());
        date = new Date();
        getItshashcode();
    }

    public String getItshashcode(){
        this.itshashcode=sha1(message,date.toString(),parenthashcode.toString());
        return itshashcode;
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
        return parenthashcode.get(0);
    }

    public void savefile(){
        File commit = join (getCommitDir(),itshashcode);
        createnewFile(commit);
        writeObject(commit,this);
    }

    /* TODO: fill in the rest of this class. */
}
