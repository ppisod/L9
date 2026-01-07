package utility;

public class V {
    public double x;
    public double y;

    public V (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public V (double magnitude, float angle) {
        this.x = magnitude * Math.cos(angle);
        this.y = magnitude * Math.sin(angle);
    }

    public V add(V other) {
        return new V(this.x + other.x, this.y + other.y);
    }

    public V sub(V other) {
        return new V(this.x - other.x, this.y - other.y);
    }

    public V mul(double scalar) {
        return new V(this.x * scalar, this.y * scalar);
    }

    public double dot(V other) {
        return this.x * other.x + this.y * other.y;
    }

    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    public V norm() {
        double m = mag();
        return m == 0 ? new V(0, 0) : new V(x / m, y / m);
    }

    public V rnd () {
        return new V((double) Math.round(x), (double) Math.round(y));
    }

    public int roundedx () {
        return Math.toIntExact(Math.round(x));
    }

    public int roundedy () {
        return Math.toIntExact(Math.round(y));
    }

    public double dist(V other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
