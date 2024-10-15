package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static gitlet.Utils.join;
import static gitlet.Repository.*;
import static gitlet.Utils.writeObject;

public class Stage implements Serializable {
    private Map<String,String> hashmap;
    private String stagename;

    public Stage (String name){
        hashmap = new HashMap<>();
        stagename = name;
    }

    public void addBlob(Blob blob){
        hashmap.put(blob.getRefs(), blob.getItshashcode());
    }

    public void savefile(){
        File stage = join(getStageDir(),stagename);
        createnewFile(stage);
        writeObject(stage,this);
    }
    public Map<String, String> getHashmap() {
        return hashmap;
    }


}
