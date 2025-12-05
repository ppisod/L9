package distortions;

import code.Distortion;

public class logisticMap extends Distortion {

    public static double logMap (double t) {
        double r = 3.7f;
        double x = t;
        for (int i = 0; i < 10; i++) {
            x = r * x * (1 - x);
        }
        return x;
    }

    @Override
    public double fnCol(double in) {
        return logMap(in);
    }

    @Override
    public double fnRow(double in) {
        return logMap(in);
    }
}
