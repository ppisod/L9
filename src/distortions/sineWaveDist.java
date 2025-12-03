package distortions;

import code.Distortion;

public class sineWaveDist extends Distortion {
    public double factor = 1;
    @Override
    public double fnCol(double in) {
        return Math.pow(Math.sin(in), factor);
    }
    @Override
    public double fnRow(double in) {
        return Math.pow(Math.sin(in), factor);
    }

//    @Override
//    public double fnPix(double in) {
//        return Math.pow(Math.log(in), factor);
//    }
}
