import java.util.ArrayList;
import java.util.Scanner;

public class Leaderboard {
    public static void main(String[] args) {

        ArrayList<Player> players = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("----- Welcome to the players Leaderboard! ----");
            System.out.println("1. Add Player");
            System.out.println("2. Show Leaderboard");
            System.out.println("3. Search Player");
            System.out.println("4. Quit");


            int choice = scan.nextInt();
            scan.nextLine();

            if (choice == 1) {
                System.out.print("Enter Player Name: ");
                String name = scan.nextLine();

                System.out.print("Enter Player Score: ");
                int score = scan.nextInt();
                scan.nextLine();

                players.add(new Player(name, score));
                System.out.println("Player added!");

            }

            //  Show Leaderboard sorted
            else if (choice == 2) {

                if (players.isEmpty()) {
                    System.out.println("No players yet.");
                    return;
                }

                // It is sorting the list of players - From highest score to lowest score
                players.sort((p1, p2) -> p2.score - p1.score);

                for (int i = 0; i < players.size(); i++) {
                    Player p = players.get(i);
                    System.out.println((i + 1) + ". " + p.name + " - " + p.score);
                }
            }


            //Search Player
            else if (choice == 3) {

                System.out.print("Enter name to search: ");
                String searchName = scan.nextLine();

                boolean found = false;

                for (Player p : players) {
                    if (p.name.equalsIgnoreCase(searchName)) {
                        System.out.println("Found: " + p.name + " - " + p.score);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Player not found.");
                }
            }

            else if (choice == 4) {
                System.out.println("Exiting... Goodbye!");
                break;
            }

            else {
                System.out.println("Invalid choice.");
            }
        }
    }

    static class Player {
        String name;
        int score;

        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}
