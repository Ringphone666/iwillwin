package gitlet;

import java.io.File;
import java.io.Serializable;
import static gitlet.Utils.*;
import static gitlet.Repository.*;
public class Blob implements Serializable {
    private String refs; //存储相对路径
    private String itshashcode;
    private byte[] content; //内容

    public String getRefs() {
        return refs;
    }

    public byte[] getContent() {
        return content;
    }

    public Blob(File file) {
        refs = file.getPath();
        content = readContents(file);
        getItshashcode();
    }



    public String  getItshashcode() {
        this.itshashcode = sha1(refs.toString(), content);
        return itshashcode;
    }

    public void savefile() {
        File blob = join(getBlobDir(), itshashcode); //后面的参数相当于名字;
        createnewFile(blob);
        writeObject(blob, this);
    }
}
