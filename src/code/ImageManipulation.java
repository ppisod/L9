package code;

import image.APImage;
import image.Pixel;
import image.PixelGetResult;

import java.util.function.Function;

public class ImageManipulation {


    static APImage image;

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {

        String fP = "/Users/jacklu/IdeaProjects/L9/cyberpunk2077.jpg";
        image = new APImage(fP);
//        image.draw();

        APImage grayscaled = ColByCol_Distort(rotateImage(image), ImageManipulation::easeIn);
        grayscaled.draw();


    }



    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static APImage grayScale(APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(w, h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // get pixel
                Pixel current = in.getPixel(i, j);
                int r = current.getRed();
                int g = current.getGreen();
                int b = current.getBlue();
                int v = (r + g + b) / 3;

                img.setPixel(i, j, new Pixel(v, v, v));
            }
        }

        return img;
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        return (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static APImage blackAndWhite(APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(w, h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Pixel real = in.getPixel(i, j);
                int avg = getAverageColour(real);
                int blackOrWhite = (avg < 128) ? 0 : 255;
                img.setPixel(i, j, new Pixel(blackOrWhite, blackOrWhite, blackOrWhite));
            }
        }
        return img;
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
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

    // my idea is to have a transformX and transformY system
    // where the image can be flipped
    // or eased
    // etc

    // Progress, from 0 to 1
    public static double reflect (double progress) {
        return 1 - progress;
    }

    public static double easeIn (double progress) {
        return progress * progress;  // From 0 to 1
    }
    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static APImage PixelByPixel_Distort (APImage in, Function<Double, Double> fn) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(w, h);

        // Pixel-by-Pixel distortion
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // we get the current progress
                int pixelIndex = j * w + i;
                double currentProgress = (double) pixelIndex / (w * h);
                // this progress is always from 0 to 1
                // therefore we can use:
                double real = fn.apply(currentProgress);
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

    public static APImage RowByRow_Distort (APImage in, Function<Double, Double> fn) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(w, h);
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                double currentJProgress = (double) j / h;
                double distortedJProgress = fn.apply(currentJProgress);
                int processedJ = (int) (distortedJProgress * h);
                if (processedJ > h) processedJ = h;
                PixelGetResult result = in.safelyGetPixel(i, processedJ);
                if (!result.has()) continue;
                img.setPixel(i, j, result.pixel());
            }
        }
        return img;
    }

    public static APImage ColByCol_Distort (APImage in, Function<Double, Double> fn) {
        int w = in.getWidth();
        int h = in.getHeight();
        APImage img = new APImage(w, h);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                double currentIProgress = (double) i / w;
                double distortedIProgress = fn.apply(currentIProgress);
                int processedI = (int) (distortedIProgress * w);
                if (processedI > w) processedI = w;
                PixelGetResult result = in.safelyGetPixel(processedI, j);
                if (!result.has()) continue;
                img.setPixel(i, j, result.pixel());
            }
        }
        return img;
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static APImage rotateImage(APImage in) {
        // bro im not doing allat
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
