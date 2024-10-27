package backend.academy.egfedo.data;

public record Vector(int x, int y) {

    public Vector add(Vector vec) {
        return new Vector(x + vec.x, y + vec.y);
    }

    public boolean inBounds(Vector lower, Vector upper) {
        return lower.x <= this.x && this.x <= upper.x && lower.y <= this.y && this.y <= upper.y;
    }
}
