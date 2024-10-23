package gitlet;


import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import static gitlet.Repository.*;
import static gitlet.Repository.createnewFile;
import static gitlet.Utils.*;



/** Represents a gitlet commit object.
 *
 *  does at a high level.
 *
 *  @author Ringphone666
 */
public class Commit implements Serializable {
    /**
     *
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
    private Map <String,String> blobhashmap;

    public Commit() {
        this.message = "initial commit";
        date = new Date(0);
        parenthashcode = new ArrayList<>();
        blobhashmap = new TreeMap<>();
        itshashcode = getItshashcode();
    }
    public Commit(String message,Commit parent) {
        this.message = message;
        date = new Date();
        parenthashcode = new ArrayList<>();
        parenthashcode.add(parent.getItshashcode());
        blobhashmap = new TreeMap<>(parent.getblobhashmap());
        itshashcode= getItshashcode();
                                           //拿到他爹的文件   那么后面commit时候利用addstage进行替换将具有相同
                                          //文件名的文件替换掉。 Blob文件虽然不在addstage中，但是可以通过map得到名称和路径
    }

    public String getItshashcode() {
        this.itshashcode=sha1(message,date.toString(),
                parenthashcode.toString(), blobhashmap.toString());
        return itshashcode;
    }

    public String getTimestamp() {
        // Thu Jan 1 00:00:00 1970 +0000
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public String getMessage() {
        return message;
    }

    public List<String> getparent() {
        return this.parenthashcode;
    }

    public String getParenthashcode() {
        return parenthashcode.get(0);
    }

    public void savefile() {
        getItshashcode();
        File commit = join (getCommitDir(), itshashcode);
        createnewFile(commit);
        writeObject(commit, this);
    }

    public Map<String, String> getblobhashmap() {
        return blobhashmap;
    }

    public void putkey(Map<String,String> map) {
        blobhashmap.putAll(map);
    }

    public void removekey(Map<String,String> map) {
        Iterator<String> iterator = blobhashmap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (map.containsKey(key)) {
                iterator.remove(); // 使用迭代器的 remove 方法安全地移除元素
            }
        }
    }

}
