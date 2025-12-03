package code;

import distortions.*;
import filters.blur;
import filters.sineWaveColors;
import image.APImage;
import image.Pixel;
import image.PixelGetResult;

public class ImageManipulation {


    static APImage image;

    public static void main(String[] args) {

        String fP = "/Users/jackl/IdeaProjects/L9/vro.jpeg";
        image = new APImage(fP);

        sigmoid Sigmoid = new sigmoid();
        scale Scale = new scale();
        Scale.factorX = 3;
        Scale.factorY = 3;

        logaspiral LogarithmicSpiral = new logaspiral();
        LogarithmicSpiral.applyC = true;

        APImage processed;

        processed = LogarithmicSpiral.derived(Scale.derived(image));
        processed.draw();

    }

    private static int getAverageColour(Pixel pixel) {
        return (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
    }

    public static APImage edgeDetection(APImage in, int threshold) {
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

    public static APImage rotateImage(APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(h, w);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                // Whole new approach here where instead of manipulating which pixel to get, we're
                // manipulating where to set it
                // (00) (01)      (10) (00)
                // (10) (11) ->   (11) (01)
                // honestly idk i copied from stackoverflow
                Pixel p = in.getPixel(x, y);
                int newX = h - 1 - y;
                //noinspection SuspiciousNameCombination
                img.setPixel(newX, x, p);
                // sus
            }
        }

        return img;
    }

}
