package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
            Readremovestage().removeblob(newblow.getRefs());
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
        Readaddstage().clear();
        Readremovestage().clear();
    }

    public static void addblob_to_commit (Commit commit){
        commit.putkey(Readaddstage().getHashmap());
    }

    public static void removeblob_to_commit (Commit commit){
        commit.removekey(Readremovestage().getHashmap());
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
        Map<String, String> addBlobs = Readaddstage().getHashmap();
        File newfile = join(CWD, filename);
        String filePath = newfile.getPath();
        Blob blob = new Blob(newfile);
//       // File newfile = join(CWD,filename);
//        // judgefileexist(newfile);
//        Blob blob = new Blob(newfile);
//        if(Readaddstage().getHashmap().containsKey(newfile.getPath())){             ////////////////?????为什么会进入这个分支？？
//            Readaddstage().removeblob(blob);
//            System.out.println("I am in here");
//        }
        if (addBlobs.containsKey(filePath)) {
            //Readaddstage().removeBlob(filePath);

//            Readaddstage().getHashmap().remove(filePath); ylf别天天惦记你那私人gethashmap哈
            Readaddstage().removeblob(filePath);
            Readaddstage().savefile();
        }
        else if(judgeremove(newfile)){//如果最新的commit中存在这个文件则进入else-if 否则进入else输出错误信息
             currentcommit = ReadHead(); //先注释，我猜测我是想读文件然后判断是否有这个玩意，但是显然judge remove现在没有传入currentcommit这是不正确的
//            currentcommit.getblobhashmap().containsKey(blob.getRefs());        //？？看不懂自己想表达什么了
            Readremovestage().addBlob(filePath,currentcommit.getblobhashmap().get(filePath));
            restrictedDelete(newfile);   ///////////////////!!!!!!!!这部分有bug尚未解决，存在无法rm后没有发现change
        }
        else {
            System.out.println("No reason to remove the file.");
            exit(0);
        }
    }

    public static void log(){
        Commit nowcommit = ReadHead();
        while (!nowcommit.getparent().isEmpty()){
            print(nowcommit);
            File a_Commit = join(COMMIT_DIR, nowcommit.getParenthashcode());
            nowcommit =  readObject(a_Commit, Commit.class);
        }
        print(nowcommit);
    }

    public static void globle_log(){
        List<String> commitfilename = Utils.plainFilenamesIn(COMMIT_DIR);
        for(String name:commitfilename){
            File a_Commit = join(COMMIT_DIR,name);
            Commit  readcommit =  readObject(a_Commit, Commit.class);
            print(readcommit);
        }
    }

    public static void find(String message){
        List<String> commitfilename = Utils.plainFilenamesIn(COMMIT_DIR);
        boolean flag = false;
        for(String name:commitfilename){
            File a_Commit = join(COMMIT_DIR,name);
            Commit  readcommit =  readObject(a_Commit, Commit.class);
            if (readcommit.getMessage().equals(message)){
                System.out.println(readcommit.getItshashcode());
                flag = true;
            }
        }
        if(!flag){
            System.out.println("Found no commit with that message.");
            exit(0);
        }
    }
    
    public static void print (Commit commit){
        System.out.println("===");
        System.out.println("commit " + commit.getItshashcode());
//        if (parents.size() > 1) {
//            String print = "Merge: " + parents.get(0).substring(0, 7)
//                    + " " + parents.get(1).substring(0, 7);
//            System.out.println(print);
//        }               尚未实现
        if (commit.getparent().size()>1){
            String print = "Merge: " + commit.getparent().get(0).substring(0,7) + " " + commit.getparent().get(1).substring(0,7);
            System.out.println(print);
        }
        System.out.println("Date: " + commit.getTimestamp());
        System.out.println(commit.getMessage());
        System.out.println();
    }

    public static boolean judgeremove(File file){  //创建一个blob，然后读取hashcode，然后查找currentcommit的map中有无这个blob，有的话则为真
        Blob blob = new Blob(file);
        currentcommit = ReadHead();
        return currentcommit.getblobhashmap().containsKey(blob.getRefs());
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
