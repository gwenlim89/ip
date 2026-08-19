import java.util.Scanner;

public class Larper {
    public static void main(String[] args) {
        String line = "____________________________________________________________";

        System.out.println(line);
        String banner = " _                              \n"
                + "| |       __ _   _ __   _ __     ___   _ __\n"
                + "| |      / _` | | '__| | '_ \\   / _ \\ | '__|\n"
                + "| |___  | (_| | | |    | |_) | |  __/ | |\n"
                + "|_____|  \\__,_| |_|    | .__/   \\___| |_|\n"
                + "                       |_|\n";
        System.out.print(banner);
        System.out.println("Fine day! I'm Larper. \n");
        System.out.println(" What can I do for you? \n");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            System.out.println(line);

            if (input.equals("exit")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            System.out.println(" " + input);
            System.out.println(line);
        }

        scanner.close();
    }
}
