package me.mdbell.bf.lol;

import me.mdbell.bf.BrainFuckVisitor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class BrainlollerReader {

    static final int[] DEFAULT_PALLET = {rgb(255, 0, 0), rgb(128, 0, 0), rgb(0, 255, 0),
            rgb(0, 128, 0), rgb(0, 0, 255), rgb(0, 0, 128), rgb(255, 255, 0),
            rgb(128, 128, 0), rgb(0, 255, 255), rgb(0, 128, 128)};

    private int[] pallet;
    private BufferedImage img;

    public BrainlollerReader(BufferedImage img) {
        this(img, DEFAULT_PALLET);
    }

    public BrainlollerReader(BufferedImage img, int[] pallet) {
        Objects.requireNonNull(img);
        Objects.requireNonNull(pallet);
        if (pallet.length != 10) {
            throw new IllegalArgumentException("Invalid pallet size!");
        }
        this.img = img;
        this.pallet = pallet;
    }

    public void accept(BrainFuckVisitor visitor) {

        if (visitor == null) {
            throw new IllegalArgumentException("null visitor!");
        }

        int x = 0;
        int y = 0;
        int deltaX = 1;
        int deltaY = 0;
        visitor.visitStart();
        while (x < img.getWidth() && y < img.getHeight() && x > -1 && y > -1) {
            int rgb = img.getRGB(x, y) & 0xFFFFFF; // use the and to ignore the alpha channel

            int idx = -1;
            for (int i = 0; i < pallet.length; i++) {
                if (rgb == pallet[i]) {
                    idx = i;
                    break;
                }
            }
            switch (idx) {
                case 0:
                    visitor.visitShift(1);
                    break;
                case 1:
                    visitor.visitShift(-1);
                    break;
                case 2:
                    visitor.visitInc(1);
                    break;
                case 3:
                    visitor.visitInc(-1);
                    break;
                case 4:
                    visitor.visitIO(false);
                    break;
                case 5:
                    visitor.visitIO(true);
                    break;
                case 6:
                    visitor.visitJump(true);
                    break;
                case 7:
                    visitor.visitJump(false);
                    break;
                case 8:
                    if (deltaX == 1) {
                        deltaX = 0;
                        deltaY = 1;
                    } else if (deltaY == 1) {
                        deltaX = -1;
                        deltaY = 0;
                    } else {
                        throw new IllegalStateException("Unimplemented 90 rotation:" + deltaX + "," + deltaY);
                    }
                    break;
                case 9:
                    if(deltaX == -1) {
                        deltaX = 0;
                        deltaY = 1;
                    }else if(deltaY == 1) {
                        deltaY = 0;
                        deltaX = 1;
                    }
                    else{
                        throw new IllegalStateException("Unimplemented reverse 90 rotation:" + deltaX + "," + deltaY);
                    }
                    break;
                case -1:
                    break; // ignore
                default:
                    throw new IllegalStateException("Unimplemented pixel index:" + idx);
            }

            x += deltaX;
            y += deltaY;
        }
        visitor.visitEnd();
    }

    private static int rgb(int r, int g, int b) {
        r = (r << 16) & 0xFF0000;
        g = (g << 8) & 0x00FF00;
        b = b & 0x0000FF;
        return r | g | b;
    }
}
