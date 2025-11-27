package code;

import image.APImage;
import image.Pixel;

public class Filter {
    public APImage p;
    public FilterMode mode = FilterMode.Single;

    public Filter (APImage image) {
        p = image;
    }

    public APImage derived (APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();
        // new AP image!
        APImage filtered = new APImage(w, h);
        if (mode == FilterMode.Single) {
            // we apply the filter to every pixel
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    filtered.setPixel(i, j, applyFilterPixel(in.getPixel(i, j)));
                }
            }
        } else if (mode == FilterMode.Sweep) {
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    // depending on sweepRange
                    // 1 2 3 4 5
                    // 6 7 8 9 1
                    // 2 3 4 5 6
                    // sweeprange = 1
                    // at i = 0; j = 0; ->
                }
            }
        }
        // TEMP
        return in;
    }

    public Pixel applyFilterPixel (Pixel in) {
        return in;
    }

    public Pixel[][] applyFilterSweep (Pixel[][] in, int sweepRange) {
        return in;
    }
}
