package distortions;

import code.Distortion;

public class reflectHoriz extends Distortion {
    @Override
    public double fnCol(double in) {
        return 1 - in;
    }
}
