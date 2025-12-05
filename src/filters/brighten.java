package filters;

import code.Filter;
import image.Pixel;

public class brighten extends Filter {
    public boolean sane = true;
    public int factor = 1;

    @Override
    public Pixel applyFilterPixel(Pixel in, int x, int y, int w, int h) {
        int r = in.getRed();
        int g = in.getGreen();
        int b = in.getBlue();
        r *= factor;
        g *= factor;
        b *= factor;
        if (sane) {
            if (r < 0) r = 0;
            if (g < 0) g = 0;
            if (b < 0) b = 0;
            if (r > 255) r = 255;
            if (g > 255) g = 255;
            if (b > 255) b = 255;
        }
        return new Pixel(r, g, b);
    }
}
