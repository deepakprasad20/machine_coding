import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.out.println("Welcome to the Battle Ship Game!!!");
        Scanner scanner = new Scanner(System.in);

        BattleShipGame battleShipGame = new BattleShipGame();

        System.out.print("Please Enter Even size N of the battlefield (NxN): ");
        int N = scanner.nextInt();
        scanner.nextLine(); // consume newline
        battleShipGame.initGame(N);


        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add Ship");
            System.out.println("2. View Battlefield");
            System.out.println("3. Start Game");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter player name (PlayerA / PlayerB): ");
                    String playerName = scanner.nextLine();

                    System.out.print("Enter ship id (e.g., SH1): ");
                    String id = scanner.nextLine();

                    System.out.print("Enter top-left X coordinate: ");
                    int x = scanner.nextInt();

                    System.out.print("Enter top-left Y coordinate: ");
                    int y = scanner.nextInt();

                    System.out.print("Enter ship size: ");
                    int size = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (battleShipGame.canPlaceShip(playerName, x, y, size)) {
                        battleShipGame.addShip(id, x, y, size, playerName);
                        System.out.println("Ship added successfully.");
                    } else {
                        System.out.println("Invalid position or overlapping ship. Try again.");
                    }
                    break;

                case 2:
                    battleShipGame.viewBattlefield();
                    break;

                case 3:
                    battleShipGame.startGame();
                    return;

                case 4:
                    System.out.println("Exiting game.");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
