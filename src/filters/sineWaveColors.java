package filters;

import code.Filter;
import image.Pixel;

public class sineWaveColors extends Filter {
    public int Factor = 10000;
    public int MaxRGB = 255;

    @Override
    public Pixel applyFilterPixel(Pixel in) {
        int r = in.getRed();
        int g = in.getGreen();
        int b = in.getBlue();
        double rv = (double) r / MaxRGB;
        double gv = (double) g / MaxRGB;
        double bv = (double) b / MaxRGB;
        return new Pixel((int)(Math.cos(rv)*Factor) + r, (int)(Math.sin(gv)*Factor) + g, (int)(Math.cos(bv)*Factor) + b);
    }
}
