package assets.images.mummy;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import java.io.IOException;

public class SpriteReformatter {
    public static int FRAME_WIDTH = 96;
    public static int FRAME_HEIGHT = 96;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: Program requires exactly 1 argument, got " + args.length + ".");
        }

        String[] line = args[0].split("\\.");
        String sourceFileName = args[0];
        String destFileName = line[0];
        String destFileExt = line[1];

        BufferedImage sourceImage;
        try {
            sourceImage = ImageIO.read(new File(sourceFileName));
        } catch (IOException ex) {
            System.out.print("Error: Source image file not found. [" + sourceFileName + "]");
            return;
        }

        BufferedImage destImage = transposeSpriteSheet(sourceImage, sourceImage.getWidth() / FRAME_WIDTH);

        try {
            ImageIO.write(destImage, "PNG", new File(destFileName + "_left." + destFileExt));
            destImage = reflectHorizontally(destImage);
            ImageIO.write(destImage, "PNG", new File(destFileName + "_right." + destFileExt));
        } catch (IOException ex) {
            System.out.println("Error: Image file cannot be written.");
        }
    }

    public static BufferedImage transposeSpriteSheet(BufferedImage img, int numFrames) {
        int newWidth = FRAME_WIDTH;
        int newHeight = FRAME_HEIGHT * numFrames;

        BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < numFrames; i++) {
            for (int y = 0; y < FRAME_HEIGHT; y++) {
                for (int x = 0; x < FRAME_WIDTH; x++) {
                    int colour = img.getRGB(x + i*FRAME_WIDTH, y);
                    newImg.setRGB(x, y + i*FRAME_HEIGHT, colour);
                }
            }
        }

        return newImg;
    }

    public static BufferedImage reflectHorizontally(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int colour = img.getRGB(x, y);
                newImg.setRGB(img.getWidth() - x - 1, y, colour);
            }
        }

        return newImg;
    }
}