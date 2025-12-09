package filters;

import image.APImage;
import image.Pixel;
import image.PixelGetResult;

public class edgeDetection {

    public int threshold = 20;

    private static int getAverageColour(Pixel pixel) {
        return (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
    }

    public APImage derived (APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(w, h);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                PixelGetResult currentPix = in.safelyGetPixel(i, j);
                PixelGetResult leftPix = in.safelyGetPixel(i - 1, j);
                PixelGetResult downPix = in.safelyGetPixel(i, j + 1);
                // if any are invalid, set to white.
                if (!currentPix.has() || !leftPix.has() || !downPix.has()) {
                    continue; // its default white
                }

                int aC = getAverageColour(currentPix.pixel());
                int lC = getAverageColour(leftPix.pixel());
                int dC = getAverageColour(downPix.pixel());

                int diff1 = Math.abs(aC - lC);
                int diff2 = Math.abs(aC - dC);

                if (diff1 > threshold || diff2 > threshold) {
                    img.setPixel(i, j, new Pixel(255, 255, 255));
                }
            }
        }
        return img;
    }
}
