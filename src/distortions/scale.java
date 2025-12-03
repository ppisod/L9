package distortions;

import code.Distortion;
import image.APImage;
import image.PixelGetResult;
public class scale extends Distortion {

    public int factorX = 1; // horizontal scale
    public int factorY = 1; // vertical scale

    @Override
    public APImage derived(APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();

        APImage out = new APImage(w*factorX, h*factorY);

        for (int i = 0; i < w*factorX; i++) {
            for (int j = 0; j < h*factorY; j++) {
                out.setPixel(i, j, in.getPixel(i/factorX, j/factorY));
            }
        }

        return out;
    }
}
