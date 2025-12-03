package distortions;

import code.Distortion;

public class binaryCounting extends Distortion {
    public boolean applyC = false;
    public boolean applyR = false;
    public double k = 2;

    public double binaryPattern(double t) {
        int bits = 4;
        double result = 0;
        int maxValue = 1 << bits; // 16 for bits=4
        int phaseInt = (int) Math.floor(t * maxValue);

        for (int i = 0; i < bits; i++) {
            double bitValue = Math.pow(k, -i - 1);
            if (((phaseInt >> i) & 1) == 1) {
                result += bitValue;
            }
        }
        return result;
    }

    @Override
    public double fnCol(double in) {
        if (!applyC) return in;
        return binaryPattern(in);
    }

    @Override
    public double fnRow(double in) {
        if (!applyR) return in;
        return binaryPattern(in);
    }
}
