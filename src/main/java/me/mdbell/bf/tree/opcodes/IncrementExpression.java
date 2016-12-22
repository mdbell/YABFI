package me.mdbell.bf.tree.opcodes;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.tree.Expression;

/**
 * @author matt123337
 */
public class IncrementExpression implements Expression {

    private int inc;

    public IncrementExpression(int inc){
        this.inc = inc;
    }

    public int getIncrement(){
        return inc;
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[inc=" + inc + "]";
    }

    @Override
    public void visit(BrainFuckVisitor visitor) {
        visitor.visitInc(inc);
    }

    public void increment(int i) {
        this.inc += i;
    }
}
