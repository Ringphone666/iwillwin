package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.*;
import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

public class Branch implements Serializable {
    private String commitpointer;
    private final String branchname;

    public Branch(Commit commit) {
        branchname = "master";
        commitpointer = commit.getItshashcode();
    }
    public Branch(Commit commit, String name) {
        branchname = name;
        commitpointer = commit.getItshashcode();
    }
    public void savefile() {
        File branch = join (getBranchDir(), branchname);
        createnewFile(branch);
        writeObject(branch, this);
    }

    public String getCommitpointer() {
        return commitpointer;
    }

    public String getBranchname() {
        return branchname;
    }
}
