package distortions;

import code.Distortion;

public class sigmoid extends Distortion {

    public static double wobblySigmoid(double t) {
        double sigmoid = 1 / (1 + (double) Math.exp(-10 * (t - 0.5)));
        double wobble = (double) Math.sin(t * Math.PI * 8) * 0.1f * t * (1 - t);
        return Math.max(0, Math.min(1, sigmoid + wobble));
    }

    @Override
    public double fnCol(double in) {
        return wobblySigmoid(in);
    }

    @Override
    public double fnRow(double in) {
        return wobblySigmoid(in);
    }
}
