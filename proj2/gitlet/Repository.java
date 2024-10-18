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
        SetHEAD();
        initMaster();
        initstage();
//        Branch master = new Branch(currentcommit);
//        master.savefile();
//        createnewFile(BRANCH_DIR);
//        writeContents(BRANCH_DIR, "master");

    }
    public static void SetHEAD(){
        createnewFile(HEAD_DIR);
        writeContents(HEAD_DIR,currentcommit.getItshashcode());
    }

    private static void initstage(){
        Stage addstage = new Stage("addstage");
        Stage removestage = new Stage("removestage");
        addstage.savefile();
        removestage.savefile();

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
        Stage addstage = Readaddstage();
        if (Readremovestage().getHashmap().containsKey(newblow.getRefs())){
            Readremovestage().removeblob(newblow);
        }
        else if (judgeadd(addstage,newblow)) {
            newblow.savefile();
            addstage.addBlob(newblow);
        }


    }
    public static void commit (String message){
        judgestage();
        judgemessage(message);
        Commit a_newcommit = new Commit(message,ReadHead());
        addblob_to_commit(a_newcommit);
        removeblob_to_commit(a_newcommit);//暂未debug
        a_newcommit.savefile();
        currentcommit = a_newcommit;
        SetHEAD();
        //设置分支？？暂定;
        clearstage();
    }
    public static Commit ReadHead(){
            File headCommit = join(COMMIT_DIR, readContentsAsString(HEAD_DIR));
            return readObject(headCommit, Commit.class);
    }
    public static void clearstage(){
        Readremovestage().getHashmap().clear();
        Readremovestage().savefile();
        Readaddstage().getHashmap().clear();
        Readaddstage().savefile();
    }

    public static void addblob_to_commit (Commit commit){
        commit.getblobhashmap().putAll(Readaddstage().getHashmap());
    }

    public static void removeblob_to_commit (Commit commit){
        commit.getblobhashmap().entrySet().removeAll(Readremovestage().getHashmap().keySet());
    }

    public static void judgestage(){
        if(Readaddstage().getHashmap().isEmpty()&&Readremovestage().getHashmap().isEmpty()){
            System.out.println("No changes added to the commit.");
            exit(0);
        }
    }
    public static void judgemessage(String message){
        if (message.isEmpty()){
            System.out.println("Please enter a commit message.");
            exit(0);
        }
    }

    public static void remove(String filename) {
        File newfile = join(CWD,filename);
        judgefileexist(newfile);
        Blob blob = new Blob(newfile);
        if(Readaddstage().getHashmap().containsKey(newfile.getPath())){
            Readaddstage().removeblob(blob);
        }
        else if(judgeremove(newfile)){//如果最新的commit中存在这个文件则进入else-if 否则进入else输出错误信息
            currentcommit.getblobhashmap().containsKey(blob.getRefs());
            restrictedDelete(newfile);
        }
        else {
            System.out.println("No reason to remove the file.");
            exit(0);
        }
    }

    public static boolean judgeremove(File file){  //创建一个blob，然后读取hashcode，然后查找currentcommit的map中有无这个blob，有的话则为真
        Blob blob = new Blob(file);
        return currentcommit.getblobhashmap().containsKey(blob.getRefs())&&currentcommit.getblobhashmap().containsValue(blob.getItshashcode());

    }
    public static void remove_addstage_file(Stage addstage,File file){  //通过移除路径来移除文件
        addstage.getHashmap().remove(file.getPath());
        addstage.savefile();
    }

        public static Stage Readaddstage (){
        File addstage =join(STAGE_DIR,"addstage");
        return readObject(addstage, Stage.class);
    }
    public static Stage Readremovestage (){
        File removestage =join(STAGE_DIR,"removestage");
        return readObject(removestage, Stage.class);
    }
    //判断这个文件是否已经存在是否要加入               hashcode和地址 如果有一个不一样则判断为真 即为加入
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
