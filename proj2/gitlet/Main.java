package gitlet;

import java.util.Objects;

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
                break;
            case "add":
                Repository.add(args[1]);
                break;
            case "rm":
                Repository.remove(args[1]);
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
            case "status":
                Repository.status();
                break;
            case "branch":
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                Repository.rm_branch(args[1]);
                break;
            case "checkout":
                checkout(args);
                break;
            case "reset":
                Repository.reset(args[1]);
                break;
        }
    }

    public static void checkout(String[] args){
        if (args.length == 3) {
            if (Objects.equals(args[1], "--")) {
                Repository.checkoutHeadFile(args[2]);
            } else {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
        } else if (args.length == 4) {
            if (Objects.equals(args[2], "--")) {
                Repository.checkoutCommitFile(args[1], args[3]);
            } else {
                System.out.println("Incorrect operands.");
                System.exit(0);
            }
        } else if (args.length == 2) {
            Repository.checkoutBranch(args[1]);
        } else {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
}
