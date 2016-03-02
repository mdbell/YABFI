package me.mdbell.bf;

/**
 * @author matt123337
 */
public interface BrainFuckVisitor {

    void visitStart();
    void visitShift(int shift);
    void visitInc(int inc);
    void visitIO(boolean input);

    void visitJump(boolean start);

    void visitEnd();
}
