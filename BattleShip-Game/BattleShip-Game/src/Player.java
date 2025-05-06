import java.util.*;

class Player {
    String name;
    List<Ship> fleet = new ArrayList<>();
    Set<String> firedCoords = new HashSet<>();

    Player(String name) {
        this.name = name;
    }

    boolean fireMissile(Player opponent, int N) {
        Random rand = new Random();
        int x, y;
        String coord;

        do {
            x = rand.nextInt(N);
            y = rand.nextInt(N);
            coord = x + "," + y;
        } while (firedCoords.contains(coord));

        firedCoords.add(coord);
        for (Ship ship : opponent.fleet) {
            if (!ship.destroyed && ship.occupies(x, y)) {
                ship.destroyed = true;
                System.out.println(name + "'s turn: Missile fired at (" + x + ", " + y + "). \"Hit\". " + opponent.name + "'s ship with id \"" + ship.id + "\" destroyed.");
                return true;
            }
        }
        System.out.println(name + "'s turn: Missile fired at (" + x + ", " + y + "). \"Miss\"");
        return false;
    }

    boolean hasShipsLeft() {
        return fleet.stream().anyMatch(s -> !s.destroyed);
    }
}

