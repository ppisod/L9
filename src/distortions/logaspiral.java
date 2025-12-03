package distortions;

import code.Distortion;

public class logaspiral extends Distortion {

    public boolean applyC = false;
    public boolean applyR = false;

    public static double logarithmicSpiral(double t) {
        // Creates a spiral-like easing
        double angle = t * Math.PI * 4;
        double radius = t * 0.5d;
        double x = Math.cos(angle) * radius + 0.5d;
        double y = Math.sin(angle) * radius;
        // Combine to get 0-1 range
        return (x + y + 1) / 2;
    }

    @Override
    public double fnCol(double in) {
        if (!applyC) return in;
        return logarithmicSpiral(in);
    }

    @Override
    public double fnRow(double in) {
        if (!applyR) return in;
        return logarithmicSpiral(in);
    }
}
