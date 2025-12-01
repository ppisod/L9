package distortions;

import code.Distortion;

public class easeIn extends Distortion {
    @Override
    public double fnCol(double in) {
        return in * in;
    }

    @Override
    public double fnRow(double in) {
        return in * in;
    }
}
