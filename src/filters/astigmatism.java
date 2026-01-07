package filters;

import code.Filter;
import image.APImage;
import image.Pixel;
import utility.V;

import java.util.ArrayList;
import java.util.Random;

public class astigmatism extends Filter {

    // this can be split into another file
    public static class XYPair {
        public int x;
        public int y;
        public XYPair (int xx, int yy) {
            x = xx; y = yy;
        }
    }

    public static float nextFloatInRangePrecise(float min, float max, Random bro) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (float) (min + (max - min) * bro.nextDouble());
    }

    public double threshold; // 0-255
    public int beamLength;
    public int beams;
    public double beamAngleOffset;
    public double beamOpacity; // 0-255
    public int bluriterations;
    public int outBlurIterations;
    public float randoAngleOffsetRange;
    public float beamContrast;
    public float CREF;

    private Random random;


    public astigmatism (double thres, int beamL, double beamO, int beams, double angleOffset, int blur, int outblur, float randomAngleOffsetRange, float beamContrast, float channelRatioExceedFactor) {
        threshold = thres;
        beamLength = beamL;
        beamOpacity = beamO;
        this.beams = beams;
        beamAngleOffset = angleOffset;
        bluriterations = blur;
        outBlurIterations = outblur;
        randoAngleOffsetRange = randomAngleOffsetRange;
        CREF = channelRatioExceedFactor;

        this.beamContrast = beamContrast;

        random = new Random();

    }

    private boolean value (Pixel p) {
        int r = p.getRed();
        int g = p.getGreen();
        int b = p.getBlue();

        float avg = (r+g+b) / 3f;
        float r_v = (float) r/((float)(g+b)/2);
        float g_v = (float) g/((float)(r+b)/2);
        float b_v = (float) b/((float)(r+g)/2);

        return r_v > CREF || g_v > CREF || b_v > CREF || avg > threshold;
    }

    private void clamp (Pixel p) {
        p.setRed(Math.clamp(p.getRed(), 0, 255));
        p.setBlue(Math.clamp(p.getBlue(), 0, 255));
        p.setGreen(Math.clamp(p.getGreen(), 0, 255));

    }

    @Override
    public APImage derived(APImage in) {

        int w = in.getWidth();
        int h = in.getHeight();

        kawaseblur blur = new kawaseblur(bluriterations); // fst blur with artifacts

        APImage blurred = blur.derived(in);
        APImage out = new APImage(w, h);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {

                Pixel pixel = in.getPixel(x, y);
                boolean value = value(pixel);

                Pixel toSet = null;
                if (value) {
//                    V current = new V (x, y);
                    XYPair curPos = new XYPair(x, y);

                    float randomOffset = nextFloatInRangePrecise(0, randoAngleOffsetRange, random);

                    for (int beam = 0; beam < beams; beam ++) {
                        // calculate angle of each beam
                        float beamAngle = (float) Math.toRadians(((360.0 / beams) * beam) + beamAngleOffset + randomOffset);
                        float beamAngleX = (float) Math.cos(beamAngle);
                        float beamAngleY = (float) Math.sin(beamAngle);
                        // add the light source color to the beam pixel positions (if the pixel position is valid)
                        // make the beam fade out according to beam length
                        ArrayList<V> applyAtVectors = new ArrayList<>();
                        ArrayList<XYPair> pairsApply = new ArrayList<>();
                        for (int wander = 1; wander <= beamLength; wander++) {

                            XYPair q = new XYPair((int) (curPos.x + (wander * beamAngleX)), (int) (curPos.y + (wander * beamAngleY)));
                            if (q.x >= w || q.x < 0 || q.y >= h || q.y < 0) continue;
                            pairsApply.add(q);

                        }

                        for (int i = 0; i < pairsApply.size(); i++) {

                            XYPair q = pairsApply.get(i);

                            double op = 1 - ((double) i / beamLength);

                            int r = pixel.getRed();
                            int g = pixel.getGreen();
                            int b = pixel.getBlue();

                            r = (int)(((((r / 255.0) - 0.5) * beamContrast) + 0.5) * 255.0);
                            g = (int)(((((g / 255.0) - 0.5) * beamContrast) + 0.5) * 255.0);
                            b = (int)(((((b / 255.0) - 0.5) * beamContrast) + 0.5) * 255.0);


                            Pixel p = out.getPixel(q.x, q.y);
                            p.setGreen((int) (p.getGreen() + (g * op * beamOpacity)));
                            p.setRed((int) (p.getRed() + (r * op * beamOpacity)));
                            p.setBlue((int) (p.getBlue() + (b * op * beamOpacity)));
                            clamp(p);
                            out.setPixel(q.x, q.y, p);

                        }
                        // each beam should be 1px width
                    }
                    toSet = blurred.getPixel(x, y);
                    // loop over beams
                } else {
                    toSet = out.getPixel(x, y);
                    Pixel b = blurred.getPixel(x, y);
                    toSet.setRed(toSet.getRed() + b.getRed());
                    toSet.setGreen(toSet.getGreen() + b.getGreen());
                    toSet.setBlue(toSet.getBlue() + b.getBlue());

                }

                assert toSet != null;
                clamp(toSet);

                out.setPixel(x, y, toSet);


            }
        }

        kawaseblur outblur = new kawaseblur(outBlurIterations);

        return outblur.derived(out);

    }

}
