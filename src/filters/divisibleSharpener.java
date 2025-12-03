package filters;

import code.Filter;
import image.Pixel;

public class divisibleSharpener extends Filter {
    public int divisibleAt = 2;
    public int giveOrTake = 0;
    public boolean applyX = false;
    public boolean applyY = false;
    public boolean applyCombined = false;
    public int factor = 10;

    @Override
    public Pixel applyFilterPixel(Pixel in, int x, int y) {
        // apply a brightness filter if x % divisibleAt < giveOrTake
        boolean toApply = false;
        if (x % divisibleAt < giveOrTake && applyX) toApply = true;
        if (y % divisibleAt < giveOrTake && applyY) toApply = true;
        if (x + y % divisibleAt < giveOrTake && applyCombined) toApply = true;
        if (!toApply) return in;
        return new Pixel(in.getRed() * factor, in.getGreen() * factor, in.getBlue() * factor);
    }
}
