package me.mdbell.bf;

/**
 * @author matt123337
 */
public abstract class AbstractBrainFuckVisitor implements BrainFuckVisitor {

    private BrainFuckVisitor visitor;

    public AbstractBrainFuckVisitor(BrainFuckVisitor visitor){
        this.visitor = visitor;
    }

    @Override
    public void visitStart() {
        visitor.visitStart();
    }

    @Override
    public void visitShift(int shift) {
        visitor.visitShift(shift);
    }

    @Override
    public void visitInc(int inc) {
        visitor.visitInc(inc);
    }

    @Override
    public void visitIO(boolean input) {
        visitor.visitIO(input);
    }

    @Override
    public void visitJump(boolean start) {
        visitor.visitJump(start);
    }

    @Override
    public void visitEnd() {
        visitor.visitEnd();
    }
}
