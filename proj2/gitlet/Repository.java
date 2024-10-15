package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

import static java.lang.System.exit;

// TODO: any imports you need here
/** Represents a gitlet repository.
 *  The repository structure:
 *  .gitlet/
 *      - objects/
 *          - commits/
 *              - ...(files of commits)
 *          - blobs/
 *              - ...(files of blobs)
 *      - branches/
 *          - master
 *          - ...(other branches)
 *      - HEAD
 *      - Stages/
 *          - addStage
 *          - removeStage
 *  does at a high level.
 *
 *  对应进行创建文件然后保存
 */
/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The objects, commits, blobs directory. */
    private static File OBJECT_DIR = join(GITLET_DIR, "objects");
    private static File COMMIT_DIR = join(OBJECT_DIR, "commits");
    private static File BLOB_DIR = join(OBJECT_DIR, "blobs");
    private static File HEAD_DIR = join (GITLET_DIR,"HEAD");
    private static File BRANCH_DIR = join (GITLET_DIR,"branches");
    private static File STAGE_DIR = join (GITLET_DIR,"stage");
    private static File NOWBRANCH_DIR = join(GITLET_DIR,"BRANCH");
    private static Commit currentcommit;
    /* TODO: fill in the rest of this class. */

    public static File getCommitDir(){
        return COMMIT_DIR;
    }
    public static File getBranchDir(){return BRANCH_DIR;}

    public static File getBlobDir() {
        return BLOB_DIR;
    }

    public static File getObjectDir() {
        return OBJECT_DIR;
    }

    public static File getHeadDir(){
        return HEAD_DIR;
    }

    public static void init(){
        if(GITLET_DIR.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            exit(0);
        }
        GITLET_DIR.mkdir();
        OBJECT_DIR.mkdir();
        COMMIT_DIR.mkdir();
        BLOB_DIR.mkdir();
        STAGE_DIR.mkdir();
        BRANCH_DIR.mkdir();
        Commit initcommit = new Commit();
        initcommit.savefile();
        currentcommit = initcommit;
        createnewFile(HEAD_DIR);
        writeContents(HEAD_DIR,currentcommit.getItshashcode());
        initMaster();
//        Branch master = new Branch(currentcommit);
//        master.savefile();
//        createnewFile(BRANCH_DIR);
//        writeContents(BRANCH_DIR, "master");

    }

    static void createnewFile(File file){
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void initMaster() {
        Branch master = new Branch(currentcommit);
        master.savefile();
        createnewFile(NOWBRANCH_DIR);
        writeContents(NOWBRANCH_DIR, "master");
    }
}
