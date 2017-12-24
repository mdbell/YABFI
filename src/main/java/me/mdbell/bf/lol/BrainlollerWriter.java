package me.mdbell.bf.lol;

import me.mdbell.bf.BrainFuckVisitor;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class BrainlollerWriter implements BrainFuckVisitor {

    private BufferedImage img;
    private int[] pallet;

    private int x = 0;
    private int y = 0;

    private int deltaX = 1;

    public BrainlollerWriter(BufferedImage img) {
        this(img, BrainlollerReader.DEFAULT_PALLET);
    }

    public BrainlollerWriter(BufferedImage img, int[] pallet) {
        Objects.requireNonNull(img);
        Objects.requireNonNull(pallet);
        if (pallet.length != 10) {
            throw new IllegalArgumentException("Invalid pallet size!");
        }
        this.img = img;
        this.pallet = pallet;
    }


    @Override
    public void visitStart() {
        x = 0;
        y = 0;
        deltaX = 1;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                img.setRGB(i, j, 0);
            }
        }
    }

    @Override
    public void visitShift(int shift) {
        if (shift == 0) {
            return;
        }
        int idx = shift < 0 ? 1 : 0;
        shift = Math.abs(shift);

        for (int i = 0; i < shift; i++) {
            setPixel(idx);
        }
    }

    @Override
    public void visitInc(int inc) {
        if (inc == 0) {
            return;
        }
        int idx = inc < 0 ? 3 : 2;
        inc = Math.abs(inc);
        for(int i = 0; i < inc; i++) {
            setPixel(idx);
        }
    }

    @Override
    public void visitIO(boolean input) {
        setPixel(input ? 5 : 4);
    }

    @Override
    public void visitJump(boolean start) {
        setPixel(start ? 6 : 7);
    }

    @Override
    public void visitEnd() {

    }

    private void setPixel(int idx) {
        if(x == img.getWidth() - 1 && deltaX == 1) {
            img.setRGB(x,y,pallet[8]);
            img.setRGB(x,y + 1, pallet[8]);
            x--;
            y++;
            deltaX = -1;
        }

        if(x == 0 && deltaX == -1) {
            deltaX = 1;
            img.setRGB(x,y,pallet[9]);
            img.setRGB(x,y + 1, pallet[9]);
            x++;
            y++;
        }
        img.setRGB(x, y, pallet[idx]);
        x += deltaX;
    }
}
