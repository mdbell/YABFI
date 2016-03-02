package me.mdbell.bf.tree.opcodes;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.tree.Expression;

/**
 * @author matt123337
 */
public class ShiftExpression implements Expression {

    private int shift;

    public ShiftExpression(int shift){
        this.shift = shift;
    }

    public int getShift(){
        return shift;
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[shift=" + shift + "]";
    }

    @Override
    public void visit(BrainFuckVisitor visitor) {
        visitor.visitShift(shift);
    }

    public void shift(int shift) {
        this.shift += shift;
    }
}
