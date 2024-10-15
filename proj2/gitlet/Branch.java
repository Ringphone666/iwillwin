package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.*;
import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

public class Branch implements Serializable {
    private String commitpointer;
    private final String Branchname;

    public Branch(Commit commit){
        Branchname = "master";
        commitpointer = commit.getItshashcode();
    }
    public Branch (Commit commit,String name){
        Branchname = name;
        commitpointer = commit.getItshashcode();
    }
    public void savefile(){
        File branch = join (getBranchDir(),Branchname);
        createnewFile(branch);
        writeObject(branch,this);
    }

    public String getCommitpointer() {
        return commitpointer;
    }
}
