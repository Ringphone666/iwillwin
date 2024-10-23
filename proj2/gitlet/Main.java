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
        if (args.length == 0)
        {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];

        switch(firstArg) {
            case "init":
                Repository.init();
                break;
            case "add":
                Repository.checkIfTheDirectoryExist();
                Repository.add(args[1]);
                break;
            case "rm":
                Repository.checkIfTheDirectoryExist();
                Repository.remove(args[1]);
                break;
            case "commit":
                Repository.checkIfTheDirectoryExist();
                Repository.commit(args[1]);
                break;
            case "log":
                Repository.checkIfTheDirectoryExist();
                Repository.log();
                break;
            case "global-log" :
                Repository.checkIfTheDirectoryExist();
                Repository.globle_log();
                break;
            case "find":
                Repository.checkIfTheDirectoryExist();
                Repository.find(args[1]);
                break;
            case "status":
                Repository.checkIfTheDirectoryExist();
                Repository.status();
                break;
            case "branch":
                Repository.checkIfTheDirectoryExist();
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                Repository.checkIfTheDirectoryExist();
                Repository.rm_branch(args[1]);
                break;
            case "checkout":
                Repository.checkIfTheDirectoryExist();
                checkout(args);
                break;
            case "reset":
                Repository.checkIfTheDirectoryExist();
                Repository.reset(args[1]);
                break;
            case "merge":
                Repository.checkIfTheDirectoryExist();
                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
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
