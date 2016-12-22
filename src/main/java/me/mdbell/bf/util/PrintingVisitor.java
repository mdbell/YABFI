package me.mdbell.bf.util;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.Opcodes;

import java.io.PrintStream;

/**
 * @author matt123337
 */
public class PrintingVisitor implements BrainFuckVisitor, Opcodes {

    protected PrintStream out;

    public PrintingVisitor(PrintStream out) {
        this.out = out;
    }

    @Override
    public void visitStart() {

    }

    @Override
    public void visitShift(int shift) {
        char c = shift < 0 ? LEFT_SHIFT : RIGHT_SHIFT;
        for (int i = 0; i < Math.abs(shift); i++) {
            print(c);
        }
    }

    @Override
    public void visitInc(int inc) {
        char c = inc < 0 ? DEC_CHAR : INC_CHAR;
        for (int i = 0; i < Math.abs(inc); i++) {
            print(c);
        }
    }

    @Override
    public void visitIO(boolean input) {
        if (input) {
            print(INPUT_CHAR);
        } else {
            print(OUTPUT_CHAR);
        }
    }

    @Override
    public void visitJump(boolean start) {
        if (start) {
            print(START_BLOCK);
        } else {
            print(END_BLOCK);
        }
    }

    @Override
    public void visitEnd() {

    }

    protected void print(char c){
        out.print(c);
    }
}
