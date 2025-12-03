package distortions;

import code.Distortion;

public class randomWalk extends Distortion {

    public boolean applyC = false;
    public boolean applyR = false;

    public static double deterministicRandomWalk(double t) {
        // Uses pseudo-random but deterministic noise
        int samples = 20;
        float sum = 0;
        for (int i = 0; i < samples; i++) {
            float freq = (float) Math.pow(2, i);
            float amp = (float) Math.pow(0.5, i);
            sum += (float) Math.sin(t * freq * Math.PI * 2) * amp;
        }
        return (sum + 1) / 2;
    }

    @Override
    public double fnCol(double in) {
        if (!applyC) return in;
        return deterministicRandomWalk(in);
    }

    @Override
    public double fnRow(double in) {
        if (!applyR) return in;
        return deterministicRandomWalk(in);
    }
}
