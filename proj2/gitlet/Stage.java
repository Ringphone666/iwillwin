package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static gitlet.Utils.join;
import static gitlet.Repository.*;
import static gitlet.Utils.writeObject;

public class Stage implements Serializable {
    private Map<String,String> hashmap;
    private String stagename;

    public Stage (String name){
        hashmap = new TreeMap<>();
        stagename = name;
    }

    public void addBlob(Blob blob){
        hashmap.put(blob.getRefs(), blob.getItshashcode());
        savefile();
    }
    public void addBlob(String refs,String hashcode){
        hashmap.put(refs, hashcode);
        savefile();
    }

    public void removeblob(Blob blob){
        hashmap.remove(blob.getRefs(),blob.getItshashcode());
        savefile();
    }

    public void removeblob(String ref){
        hashmap.remove(ref);
        savefile();
    }


    public void savefile(){
        File stage = join(getStageDir(),stagename);
        createnewFile(stage);
        writeObject(stage,this);
    }
    public Map<String, String> getHashmap() {
        return hashmap;
    }

    public void clear (){
        hashmap.clear();        //妈的蠢猪 你妈怎么会想到getHashmap然后clear，在那debug大半天
        savefile();
    }

}
