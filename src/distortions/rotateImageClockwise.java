package distortions;

import image.APImage;
import image.Pixel;

public class rotateImageClockwise {
    public static APImage derived (APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(h, w);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Pixel p = in.getPixel(x, y);
                int newX = h - 1 - y;
                //noinspection SuspiciousNameCombination
                img.setPixel(newX, x, p);
            }
        }

        return img;
    }
}
