package gitlet;

public class Blob {
    private String refs;//存储相对路径

    private byte[] content;//内容

    public String getRefs(){
        return refs;
    }

    public byte[] getContent() {
        return content;
    }

}
