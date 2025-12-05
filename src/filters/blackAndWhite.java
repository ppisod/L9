package filters;

import code.Filter;
import image.Pixel;

public class blackAndWhite extends Filter {
    @Override
    public Pixel applyFilterPixel(Pixel in, int x, int y, int w, int h) {
        int avg = (in.getRed() + in.getGreen() + in.getBlue()) / 255;
        int blackOrWhite = (avg < 128) ? 0 : 255;
        return new Pixel(blackOrWhite, blackOrWhite, blackOrWhite);
    }
}
