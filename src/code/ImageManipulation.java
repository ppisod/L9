package code;

import distortions.*;
import filters.*;
import image.APImage;
import image.Pixel;
import image.PixelGetResult;

public class ImageManipulation {


    static APImage image;

    public static void main(String[] args) {

        // Cool Presets!!!
//        divisibleSharpener sharpener = new divisibleSharpener();
//        sharpener.applySineBoomY = true;
//        sharpener.sineBoomFactor = 100000;
//        sharpener.factor = 2;
//        sharpener.divisibleAt = 5;
//        sharpener.giveOrTake = 1;
//        sharpener.sane = false;

        String fP = "/Users/jackl/IdeaProjects/L9/traffic.jpeg";
        image = new APImage(fP);

        scale Scale = new scale();
        Scale.factorX = 3;
        Scale.factorY = 3;

        add addFilter = new add();
        addFilter.mode = BlendMode.Average;

        blackAndWhite bl = new blackAndWhite();

        astigmatism stigmaFilter = new astigmatism(225.0, 125, 0.015, 4, 36, 1, 1, 10, 5, 2.5f);

        divisibleSharpener sharpener = new divisibleSharpener();
        sharpener.applySineBoomY = true;
        sharpener.sineBoomFactor = 100000;
        sharpener.factor = 2;
        sharpener.divisibleAt = 5;
        sharpener.giveOrTake = 1;
        sharpener.sane = false;


        brightnessmap bmap = new brightnessmap();
        bmap.thr = 180;

        APImage original = Scale.derived(image);

        APImage processed = Scale.derived(stigmaFilter.derived(image));

        mix Mix = new mix(processed, 0.7F);

        original = Mix.derived(original);
        original.draw();

    }

}
