import java.util.Scanner;

public class Larper {

    public static class Task {
        private String description;
        private boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public String getDescription() {
            return description;
        }

        public boolean isDone() {
            return isDone;
        }

        public void markAsDone() {
            isDone = true;
        }

        public void unmarkAsDone() {
            isDone = false;
        }
    }

    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        String line = "_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_";

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
        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            System.out.println(line);

            if (input.equals("exit")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            if (input.equals("list")){
                int count = 0;
                while (count < taskCount){
                    System.out.println((count + 1) + ". " +  " [" + (tasks[count].isDone() ? "X" : " ") + "]" + tasks[count].getDescription());
                    count++;
                }
                System.out.println(line);
            }else if(input.contains("mark")){
                int number = Integer.parseInt(input.substring(5, input.length()));
                tasks[number - 1].markAsDone();
                System.out.println("Marked task " + number + " as done.");
                System.out.println(line);

            }else if(input.contains("unmark")){
                int number = Integer.parseInt(input.substring(5, input.length()));
                tasks[number - 1].unmarkAsDone();
                System.out.println("Unmarked task " + number + " as done.");
                System.out.println(line);

            }else{
                tasks[taskCount] = new Task(input);
                System.out.println("added: " + input);
                System.out.println(line);
                taskCount++;
            }
            
        }

        scanner.close();
    }
}
