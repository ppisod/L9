package filters;

import code.Filter;
import image.Pixel;

public class divisibleSharpener extends Filter {
    public int divisibleAt = 2;
    public int giveOrTake = 0;

    public boolean applyX = false;
    public boolean applyY = false;
    public boolean applyCombined = false;
    public boolean applyMult = false;
    public boolean applyDiv = false;

    public boolean applySineBoomX = false;
    public boolean applySineBoomY = false;
    public int sineBoomFactor = 100;

    public int factor = 10;
    public boolean sane = true;

    @Override
    public Pixel applyFilterPixel(Pixel in, int x, int y, int w, int h) {
        // apply a brightness filter if x % divisibleAt < giveOrTake
        boolean toApply = false;
        double applyFactor = 1;
        if (x % divisibleAt < giveOrTake && applyX) toApply = true;
        if (y % divisibleAt < giveOrTake && applyY) toApply = true;
        if ((x + y) % divisibleAt < giveOrTake && applyCombined) toApply = true;
        if ((x * y) % divisibleAt < giveOrTake && applyMult) toApply = true;
        if (x > 0 && y > 0 && (x / y) % divisibleAt < giveOrTake && applyDiv) toApply = true;

        if (applySineBoomX) {
            toApply = true;
            applyFactor = ((Math.sin((double) x / w) * sineBoomFactor) % divisibleAt) / divisibleAt;
        }

        if (applySineBoomY) {
            toApply = true;
            applyFactor = ((Math.sin((double) y / h) * sineBoomFactor) % divisibleAt) / divisibleAt;
        }

        if (!toApply) return in;
        int nr = (int) (in.getRed() + ((in.getRed() * factor) - in.getRed()) * applyFactor);
        int ng = (int) (in.getGreen() + ((in.getGreen() * factor) - in.getGreen()) * applyFactor);
        int nb = (int) (in.getBlue() + ((in.getBlue() * factor) - in.getBlue()) * applyFactor);
        if (sane) return new Pixel(Math.clamp(nr, 0, 255), Math.clamp(ng, 0, 255), Math.clamp(nb, 0, 255));
        return new Pixel(nr, ng, nb);
    }
}
