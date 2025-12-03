package distortions;

import code.Distortion;

public class sineWaveDistOppos extends Distortion {
    public double factor = 1;
    @Override
    public double fnCol(double in) {
        return 1 - Math.pow(Math.sin(in), factor);
    }
    @Override
    public double fnRow(double in) {
        return 1 - Math.pow(Math.sin(in), factor);
    }
}
