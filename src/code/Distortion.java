package code;

import image.APImage;
import image.PixelGetResult;

public class Distortion {

    // overrideable functions
    public double fnCol(double in) {
        return in;
    }

    public double fnRow (double in) {
        return in;
    }

    public double fnPix (double in) {
        return in;
    }

    public APImage derived(APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(w, h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                double currentIProgress = (double) i / w;
                double distortedIProgress = fnCol(currentIProgress);
                int processedI = (int) (distortedIProgress * w);
                if (processedI > w) processedI = w;
                PixelGetResult result = in.safelyGetPixel(processedI, j);
                if (!result.has()) continue;
                img.setPixel(i, j, result.pixel());
            }
        }

        in = img;
        img = new APImage(w, h);

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                double currentJProgress = (double) j / h;
                double distortedJProgress = fnRow(currentJProgress);
                int processedJ = (int) (distortedJProgress * h);
                if (processedJ > h) processedJ = h;
                PixelGetResult result = in.safelyGetPixel(i, processedJ);
                if (!result.has()) continue;
                img.setPixel(i, j, result.pixel());
            }
        }

        in = img;
        img = new APImage(w, h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // we get the current progress
                int pixelIndex = j * w + i;
                double currentProgress = (double) pixelIndex / (w * h);
                // this progress is always from 0 to 1
                // therefore we can use:
                double real = fnPix(currentProgress);
                // we transform this 0 to 1 value back to the pixel index with:
                int pixelReference = (int) (real * w * h);
                if (pixelReference > w * h) {
                    pixelReference = w * h;
                }
                int referenceX = pixelReference % w;
                int referenceY = pixelReference / w;

                PixelGetResult result = in.safelyGetPixel(referenceX, referenceY);
                if (!result.has()) continue; // idk
                img.setPixel(i, j, result.pixel());

            }
        }

        return img;

    }

}
