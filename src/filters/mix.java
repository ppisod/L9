package filters;

import code.Filter;
import image.APImage;
import image.Pixel;

public class mix extends Filter {
    public APImage other;
    public float dix;

    public mix (APImage other, float m) {
        this.other = other;
        dix = m;
    }

    @Override
    public Pixel applyFilterPixel(Pixel in, int x, int y, int w, int h) {
        Pixel ot = other.getPixel(x, y);
        int or = ot.getRed();
        int og = ot.getGreen();
        int ob = ot.getBlue();
        int tr = in.getRed();
        int tg = in.getGreen();
        int tb = in.getBlue();
        return new Pixel((int) ((or * dix) + (tr * (1-dix))), (int) ((og * dix) + (tg * (1-dix))), (int) ((ob * dix) + (tg * (1-dix))));

    }
}
