package code;

import distortions.*;
import filters.*;
import image.APImage;
import image.Pixel;
import image.PixelGetResult;

public class ImageManipulation {


    static APImage image;

    public static void main(String[] args) {

        // PRESETS
//        divisibleSharpener sharpener = new divisibleSharpener();
//        sharpener.applySineBoomY = true;
//        sharpener.sineBoomFactor = 100000;
//        sharpener.factor = 2;
//        sharpener.divisibleAt = 5;
//        sharpener.giveOrTake = 1;
//        sharpener.sane = false;

        String fP = "/Users/jackl/IdeaProjects/L9/vro.jpeg";
        image = new APImage(fP);

        Distortion noDist = new Distortion();

        sigmoid Sigmoid = new sigmoid();

        scale Scale = new scale();
        Scale.factorX = 3;
        Scale.factorY = 3;

        scale afterScale = new scale();
        afterScale.factorX = 2;
        afterScale.factorY = 2;

        bounce bounc = new bounce();
        bounc.pixOn = false;

        logaspiral LogarithmicSpiral = new logaspiral();
        LogarithmicSpiral.applyC = true;
        LogarithmicSpiral.applyR = true;

        randomWalk RandomWalk = new randomWalk();
        RandomWalk.applyC = true;
        RandomWalk.applyR = true;
        RandomWalk.s = 22;

        binaryCounting BinaryCounting = new binaryCounting();
        BinaryCounting.applyC = true;
        BinaryCounting.k = 2;

        divisibleSharpener sharpener = new divisibleSharpener();
        sharpener.applySineBoomY = true;
        sharpener.applyMult = true;
        sharpener.sineBoomFactor = 50;
        sharpener.factor = 2;
        sharpener.divisibleAt = 5;
        sharpener.giveOrTake = 1;
        sharpener.sane = false;

        sineWaveColors k = new sineWaveColors();
        k.MaxRGB = 2033;
        k.Factor = 2000;

        brighten Brighten = new brighten();
        Brighten.factor = 2;
        Brighten.sane = true;

        APImage processed;

        add addFilter = new add();
        addFilter.mode = BlendMode.Average;

        processed = addFilter.derived(edgeDetection(Scale.derived(image), 20), Scale.derived(image));
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
                Pixel p = in.getPixel(x, y);
                int newX = h - 1 - y;
                //noinspection SuspiciousNameCombination
                img.setPixel(newX, x, p);
            }
        }

        return img;
    }

}
