package filters;

import code.Filter;
import code.FilterMode;
import image.Pixel;

public class blur extends Filter {
    public blur () {
        mode = FilterMode.Sweep;
    }

    @Override
    public Pixel[] applyFilterSweep(Pixel[] in) {
        Pixel[] pixels = new Pixel[in.length];
        int tR = 0;
        int tG = 0;
        int tB = 0;
        int totalPixels = 0;
        for (int i = 0; i < in.length; i++) {
            if (in[i] == null) continue;
            tR += in[i].getRed();
            tG += in[i].getGreen();
            tB += in[i].getBlue();
            totalPixels++;
        }
        int r = tR / totalPixels;
        int g = tG / totalPixels;
        int b = tB / totalPixels;

        int row = ((sweepDistance*2)+1);
        int middle = (((sweepDistance*2)+1)/2);
        pixels[middle + (row * middle)] = new Pixel(r, g, b);

        return pixels;
    }
}
