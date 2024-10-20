package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];

        switch(firstArg) {
            case "init":
                Repository.init();
                // TODO: handle the `init` command
                break;
            case "add":
                Repository.add(args[1]);
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "rm":
                Repository.remove(args[1]);
                //
                break;
            case "commit":
                Repository.commit(args[1]);
                break;
            case "log":
                Repository.log();
                break;
            case "global-log" :
                Repository.globle_log();
                break;
            case "find":
                Repository.find(args[1]);
                break;
        }
    }
}
