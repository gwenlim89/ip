import java.util.Scanner;

public class Larper {
    public static void main(String[] args) {

        System.out.println("____________________________________________________________ \n");
        String banner = " _                              \n"
                + "| |       __ _   _ __   _ __     ___   _ __\n"
                + "| |      / _` | | '__| | '_ \\   / _ \\ | '__|\n"
                + "| |___  | (_| | | |    | |_) | |  __/ | |\n"
                + "|_____|  \\__,_| |_|    | .__/   \\___| |_|\n"
                + "                       |_|\n";
        System.out.println(banner);
        System.out.println("\t Fine day! I'm Larper. \n");
        System.out.println("\t What can I do for you? \n");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }   
            System.out.println("____________________________________________________________ \n");
            System.out.println("\t explain " + input);
            System.out.println("____________________________________________________________ \n");
        }

        System.out.println("____________________________________________________________ \n");
        System.out.println(" \t Bye. Hope to see you again soon!");


    }
}
