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

        String fP = "/Users/jackl/IdeaProjects/L9/vro.jpeg";
        image = new APImage(fP);

        scale Scale = new scale();
        Scale.factorX = 3;
        Scale.factorY = 3;

        add addFilter = new add();
        addFilter.mode = BlendMode.Average;

        edgeDetection edgeDetector = new edgeDetection();

        APImage processed;
        processed = addFilter.derived(edgeDetector.derived(Scale.derived(image)), Scale.derived(image));
        processed.draw();

    }

}
