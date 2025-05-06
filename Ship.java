class Ship {
    String id;
    int x, y, size;
    boolean destroyed = false;

    Ship(String id, int x, int y, int size) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    boolean occupies(int i, int j) {
        return i >= x && i < x + size && j >= y && j < y + size;
    }

    public boolean occupiesCoordinate(int x, int y) {
        return x >= this.x && x < this.x + size && y >= this.y && y < this.y + size;
    }

}
