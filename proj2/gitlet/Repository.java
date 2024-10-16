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

    public static File getStageDir(){
        return STAGE_DIR;
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
        initstage();
//        Branch master = new Branch(currentcommit);
//        master.savefile();
//        createnewFile(BRANCH_DIR);
//        writeContents(BRANCH_DIR, "master");

    }

    private static void initstage(){
        Stage addstage = new Stage("addstage");
        addstage.savefile();

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

    public static void add(String filename){
        File newfile = join(CWD,filename);
        judgefileexist(newfile); //判断这个文件是否存在
        Blob newblow = new Blob(newfile);
        newblow.savefile();
        Stage addstage = Readaddstage();
        if (judgeadd(addstage,newblow)){  //如果名字和hashcode都一样则存入
            addstage.getHashmap().put(newblow.getRefs(), newblow.getItshashcode());
        }
        addstage.savefile();
    }

    public static Stage Readaddstage (){
        File addstage =join(STAGE_DIR,"addstage");
        return readObject(addstage, Stage.class);
    }
    //判断这个文件是否已经存在是否要加入
    public static boolean judgeadd(Stage stage,Blob blob){
        if(!(stage.getHashmap().containsValue(blob.getItshashcode())&&stage.getHashmap().containsKey(blob.getRefs()))){
            return true;
        }
        else
            return false;
    }
    public static void judgefileexist(File file){
        if(file.exists()){
            return;
        }
        else{
            System.out.println("File does not exist.");
            System.exit(0);
        }
    }
}
