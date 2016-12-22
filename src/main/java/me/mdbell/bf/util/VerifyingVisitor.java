package me.mdbell.bf.util;

import me.mdbell.bf.AbstractBrainFuckVisitor;
import me.mdbell.bf.BrainFuckVisitor;

/**
 * @author matt123337
 */
public class VerifyingVisitor extends AbstractBrainFuckVisitor {

    private int jumpDepth = 0;

    public VerifyingVisitor(BrainFuckVisitor visitor) {
        super(visitor);
    }

    @Override
    public void visitStart() {
        super.visitStart();
    }

    @Override
    public void visitShift(int shift) {
        super.visitShift(shift);
    }

    @Override
    public void visitInc(int inc) {
        super.visitInc(inc);
    }

    @Override
    public void visitIO(boolean input) {
        super.visitIO(input);
    }

    @Override
    public void visitJump(boolean start) {
        if (start) {
            jumpDepth++;
        } else {
            jumpDepth--;
        }
        if (jumpDepth < 0) {
            throw new VerifyException("Unexpected end of loop!");
        }
        super.visitJump(start);
    }

    @Override
    public void visitEnd() {
        if (jumpDepth != 0) {
            throw new VerifyException("Unclosed loop!");
        }
        super.visitEnd();
    }
}
