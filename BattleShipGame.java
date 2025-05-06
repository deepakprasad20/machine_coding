import java.util.Arrays;
import java.util.Random;

public class BattleShipGame {
    int N;
    String[][] battlefield;
    Player playerA = new Player("PlayerA");
    Player playerB = new Player("PlayerB");

    Random random = new Random();

    public void initGame(int n) {
        this.N = n;
        battlefield = new String[n][n];
        for(int i = 0 ; i < N; i++){
            for(int j = 0 ; j < N; j++){
                battlefield[i][j] = "~~~~~~";
            }
        }
    }

    public boolean canPlaceShip(String playerName, int x, int y, int size) {
        // Check boundaries
        if (x < 0 || y < 0 || x + size > N || y + size > N)
            return false;

        // Ensure no overlap
        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (!battlefield[i][j].equals("~~~~~~"))
                    return false;
            }
        }

        // Ensure ship in correct player's half
        if (playerName.equals("PlayerA") && y >= N / 2)
            return false;
        if (playerName.equals("PlayerB") && y < N / 2)
            return false;

        return true;
    }

    public void addShip(String id, int x, int y, int size, String playerName) {
        Ship ship = new Ship(id, x, y, size);
        Player player = playerName.equals("PlayerA") ? playerA : playerB;
        player.fleet.add(ship);

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (playerName.equals("PlayerA"))
                    battlefield[i][j] = "A-" + id;
                else
                    battlefield[i][j] = "B-" + id;
            }
        }
    }

    public void startGame() {
        boolean playerAFlag = true;
        while(playerA.hasShipsLeft() && playerB.hasShipsLeft()){
            if(playerAFlag){
                playerA.fireMissile(playerB, N);

            }else{
                playerB.fireMissile(playerA, N);
            }
            viewBattlefield();
            try{
                Thread.sleep(3000);
            } catch (Exception e){

            }
            playerAFlag = !playerAFlag;
        }
        System.out.println("Game Ended!!!!");
        if(playerA.hasShipsLeft()){
            System.out.println("Player A Won!!!!");
        } else{
            System.out.println("Player B Won!!!!");
        }
    }

//    public void viewBattlefield() {
//        System.out.println("\nCurrent Battlefield:");
//
//        // Print column headers
//        System.out.print("      ");
//        for (int col = 0; col < N; col++) {
//            System.out.printf("| %4d ", col);
//        }
//        System.out.println("|");
//
//        // Print top border
//        System.out.print("--------");
//        for (int col = 0; col < N; col++) {
//            System.out.print("+-------");
//        }
//        System.out.println("+");
//
//        // Print each row
//        for (int row = 0; row < N; row++) {
//            System.out.printf("%6d  ", row); // Row label
//
//            for (int col = 0; col < N; col++) {
//                String content = battlefield[row][col];
//                for (Player player : Arrays.asList(playerA, playerB)) {
//                    for (Ship ship : player.fleet) {
//                        if (!ship.destroyed && ship.occupiesCoordinate(row, col)) {
//                            display = String.format("%s", ship.id);
//                        }
//                    }
//                }
//                System.out.printf("|%-6s", content);
//            }
//            System.out.println("|");
//
//            // Row separator
//            System.out.print("-------");
//            for (int col = 0; col < N; col++) {
//                System.out.print("+------");
//            }
//            System.out.println("+");
//        }
//    }

    public void viewBattlefield() {
        System.out.println("\nCurrent Battlefield (Only Alive Ships):");

        // Print column headers
        System.out.print("     ");
        for (int col = 0; col < N; col++) {
            System.out.printf("| %2d ", col);
        }
        System.out.println("|");

        // Print top border
        System.out.print("-----");
        for (int col = 0; col < N; col++) {
            System.out.print("+----");
        }
        System.out.println("+");

        for (int row = 0; row < N; row++) {
            System.out.printf("%3d  ", row); // Row label

            for (int col = 0; col < N; col++) {
                String display = "    ";
                for (Player player : Arrays.asList(playerA, playerB)) {
                    String p_id = player == playerA ? "A-" : "B-";
                    for (Ship ship : player.fleet) {
                        if (!ship.destroyed && ship.occupiesCoordinate(row, col)) {
                            display = String.format("%s", p_id + ship.id);
                        }
                    }
                }
                System.out.printf("|%-4s", display);
            }

            System.out.println("|");

            // Row separator
            System.out.print("-----");
            for (int col = 0; col < N; col++) {
                System.out.print("+----");
            }
            System.out.println("+");
        }
    }

}