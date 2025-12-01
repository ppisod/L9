package distortions;

import code.Distortion;

public class reflectVert extends Distortion {
    @Override
    public double fnRow(double in) {
        return 1 - in;
    }
}
