package filters;

import code.Filter;
import image.Pixel;

public class brightnessmap extends Filter {

    public int thr = 230;

    private double value (Pixel p) {
        return (p.getRed() + p.getGreen() + p.getBlue()) / 3f;
    }
    @Override
    public Pixel applyFilterPixel(Pixel in, int x, int y, int w, int h) {
        return value(in) > thr ? new Pixel(255, 255, 255) : new Pixel(0, 0, 0);
    }
}
