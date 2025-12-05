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
                // 0 to 1
                double denomW = Math.max(1, w - 1);
                double currentIProgress = i / denomW;
                double distortedIProgress = fnCol(currentIProgress);
                int processedI = (int) Math.round(distortedIProgress * (w - 1));
                if (processedI < 0) processedI = 0;
                if (processedI > w - 1) processedI = w - 1;
                PixelGetResult result = in.safelyGetPixel(processedI, j);
                if (!result.has()) continue;
                img.setPixel(i, j, result.pixel());
            }
        }

        in = img;
        img = new APImage(w, h);

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                double denomH = Math.max(1, h - 1);
                double currentJProgress = j / denomH;
                double distortedJProgress = fnRow(currentJProgress);
                int processedJ = (int) Math.round(distortedJProgress * (h - 1));
                if (processedJ < 0) processedJ = 0;
                if (processedJ > h - 1) processedJ = h - 1;
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
                int total = w * h;
                double denomP = Math.max(1, total - 1);
                double currentProgress = pixelIndex / (double) denomP;
                // this progress is always from 0 to 1
                // therefore we can use:
                double real = fnPix(currentProgress);
                // we transform this 0 to 1 value back to the pixel index with:
                int pixelReference = (int) Math.round(real * (total - 1));
                if (pixelReference < 0) pixelReference = 0;
                if (pixelReference > total - 1) pixelReference = total - 1;
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
