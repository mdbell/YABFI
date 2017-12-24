package me.mdbell.bf.util;

import me.mdbell.bf.AbstractBrainFuckVisitor;
import me.mdbell.bf.BrainFuckVisitor;

public class CountingVisitor extends AbstractBrainFuckVisitor{

    private int count;

    public CountingVisitor(BrainFuckVisitor visitor) {
        super(visitor);
    }

    @Override
    public void visitStart() {
        super.visitStart();
        count = 0;
    }

    @Override
    public void visitShift(int shift) {
        super.visitShift(shift);
        count += Math.abs(shift);
    }

    @Override
    public void visitInc(int inc) {
        super.visitInc(inc);
        count += Math.abs(inc);
    }

    @Override
    public void visitIO(boolean input) {
        super.visitIO(input);
        count++;
    }

    @Override
    public void visitJump(boolean start) {
        super.visitJump(start);
        count++;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    public final int getCount(){
        return count;
    }
}
