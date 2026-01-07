package filters;

import code.Filter;
import code.FilterMode;
import image.APImage;
import image.Pixel;

public class kawaseblur extends Filter {

    private final int iterations;

    public kawaseblur (int iterations) {
        this.iterations = iterations;
        this.mode = FilterMode.Single;
    }

    @Override
    public APImage derived(APImage in) {
        APImage current = in;

        for (int i = 0; i < iterations; i++) {
            int offset = 1 << i; // 1, 2, 4, 8...
            current = kawasePass(current, offset);
        }

        return current;
    }

    private APImage kawasePass(APImage src, int offset) {
        int w = src.getWidth();
        int h = src.getHeight();
        APImage dst = new APImage(w, h);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                // Sample 4 corners
                int x1 = Math.max(0, x - offset);
                int x2 = Math.min(w - 1, x + offset);
                int y1 = Math.max(0, y - offset);
                int y2 = Math.min(h - 1, y + offset);

                Pixel p1 = src.getPixel(x1, y1);
                Pixel p2 = src.getPixel(x2, y1);
                Pixel p3 = src.getPixel(x1, y2);
                Pixel p4 = src.getPixel(x2, y2);

                int avgRed = (p1.getRed() + p2.getRed() + p3.getRed() + p4.getRed()) / 4;
                int avgGreen = (p1.getGreen() + p2.getGreen() + p3.getGreen() + p4.getGreen()) / 4;
                int avgBlue = (p1.getBlue() + p2.getBlue() + p3.getBlue() + p4.getBlue()) / 4;

                dst.setPixel(x, y, new Pixel(avgRed, avgGreen, avgBlue));
            }
        }

        return dst;
    }
}