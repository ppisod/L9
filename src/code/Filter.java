package code;

import image.APImage;
import image.Coord;
import image.Pixel;
import image.PixelGetResult;

import java.security.InvalidParameterException;

public class Filter {
    // these properties are public
    // for reading and writing
    public FilterMode mode = FilterMode.Single;
    public int sweepDistance = 1;

    public APImage derived (APImage in) {
        int w = in.getWidth();
        int h = in.getHeight();
        // new AP image!
        APImage filtered = new APImage(w, h);
        if (mode == FilterMode.Single) {
            // we apply the filter to every pixel
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    filtered.setPixel(i, j, applyFilterPixel(in.getPixel(i, j), i, j, w, h));
                }
            }
        } else if (mode == FilterMode.Sweep) {
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {

                    // apply sweep methods
                    Coord[] sweepR = getSweepRange(i, j, sweepDistance);
                    Pixel[] pixels = getSweep(in, sweepR);
                    pixels = applyFilterSweep(pixels);
                    setSweep(filtered, pixels, sweepR);

                }
            }
        }
        // TEMP
        return filtered;
    }

    private Coord[] getSweepRange (int x, int y, int range) {
        Coord[] r = new Coord[(range * 2 + 1) * (range * 2 + 1)];
        // o o p o o
        // o o p o o
        // p p p p p
        // o o p o o
        // o o p o o
        // x = 3, y = 2, r = 2
        int g = 0;
        for ( int i = x - range; i < x + range; i++ ) {
            for ( int j = y - range; j < y + range; j++ ) {
                r[g] = new Coord(i, j);
                g++;
            }
        }

        return r;
    }

    // return can have nulls
    private Pixel[] getSweep (APImage originalImage, Coord[] sweep) {
        Pixel[] p = new Pixel[sweep.length];


        for (int i = 0; i < sweep.length; i++) {
            if (sweep[i] == null) continue;
            PixelGetResult currentPixel = originalImage.safelyGetPixel(sweep[i].x(), sweep[i].y());
            if (!currentPixel.has()) {
                continue;
            }
            p[i] = currentPixel.pixel();
        }

        // everywhere where there is a null? set the sweep coord to null too
        for (int i = 0; i < p.length; i++) {
            if (p[i] == null) sweep[i] = null;
        }

        // process

        return p;
    }


    // input wanted must match shape of sweepUpdated so where sweepUpdated is null wanted must also be null!
    private void setSweep (APImage toModify, Pixel[] wanted, Coord[] sweepUpdated) {

        if (wanted.length != sweepUpdated.length) throw new InvalidParameterException("wanted must be same shape as sweep!");

        for (int i = 0; i < wanted.length; i++) {
            // we ignore it if the coordinate says that it is out of bounds
            if (sweepUpdated[i] == null) continue;

            toModify.safelySetPixel(sweepUpdated[i].x(), sweepUpdated[i].y(), wanted[i]);
        }

    }

    public Pixel applyFilterPixel (Pixel in, int x, int y, int w, int h) {
        return in;
    }

    // THERE SHOULD ALWAYS BE NULL-IGNORE CHECKS IN THE LOOP whdn you are iterating through it!
    // actually it isn't that important haha
    // the result is ignored if no coord exists at that position
    // and even if you write to the coord[] then
    // it still writes the pixel safely
    // Feel free to do whatever honestly
    // keep in mind that there are nulls in Pixel[] in.


    // output should always be same shape. but it will always be because you can't just randomly resize an array, can you
    public Pixel[] applyFilterSweep (Pixel[] in) {
        return in;
    }
}
