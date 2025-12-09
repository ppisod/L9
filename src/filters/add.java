package filters;

import code.BlendMode;
import code.Filter;
import image.APImage;
import image.Pixel;

public class add {
    public BlendMode mode = BlendMode.SumSane;

    public APImage derived (APImage a, APImage b) {
        int aW = a.getWidth(); int aH = a.getHeight();
        int bW = b.getWidth(); int bH = b.getHeight();
        if (aW != bW) throw new UnsupportedOperationException("dims must be equal");
        if (aH != bH) throw new UnsupportedOperationException("dims must be equal");
        APImage res = new APImage(aW, aH);
        for (int i = 0; i < aW; i++) {
            for (int j = 0; j < aH; j++) {
                Pixel pA = a.getPixel(i, j);
                Pixel pB = b.getPixel(i, j);
                int red = pA.getRed() + pB.getRed();
                int gre = pA.getGreen() + pB.getGreen();
                int blu = pA.getBlue() + pB.getBlue();
                if (mode == BlendMode.SumSane) {
                    res.setPixel(i, j, new Pixel(Math.clamp(red, 0, 255), Math.clamp(gre, 0, 255), Math.clamp(blu, 0, 255)));
                } else if (mode == BlendMode.SumInsane) {
                    res.setPixel(i, j, new Pixel(red, gre, blu));
                } else {
                    res.setPixel(i, j, new Pixel(red/2, gre/2, blu/2));
                }
            }
        }
        return res;
    }

}
