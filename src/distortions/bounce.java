package distortions;

import code.Distortion;

public class bounce extends Distortion {
    public boolean colOn = true;
    public boolean rowOn = true;
    public boolean pixOn = true;
    public boolean inverse = false;
    public double fn (double x) {
        double n1 = 7.5625;
        double d1 = 2.75;

        if (x < 1 / d1) {
            return n1 * x * x;
        } else if (x < 2 / d1) {
            return n1 * (x -= 1.5 / d1) * x + 0.75;
        } else if (x < 2.5 / d1) {
            return n1 * (x -= 2.25 / d1) * x + 0.9375;
        } else {
            return n1 * (x -= 2.625 / d1) * x + 0.984375;
        }
    }
    @Override
    public double fnCol(double in) {
        if (!colOn) return super.fnCol(in);
        return inverse ? 1 - fn(in) : fn(in);
    }

    @Override
    public double fnRow(double in) {
        if (!rowOn) return super.fnRow(in);
        return inverse ? 1 - fn(in) : fn(in);
    }

    @Override
    public double fnPix(double in) {
        if (!pixOn) return super.fnPix(in);
        return inverse ? 1 - fn(in) : fn(in);
    }
}
