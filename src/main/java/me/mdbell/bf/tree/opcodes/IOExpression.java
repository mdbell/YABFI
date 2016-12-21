package me.mdbell.bf.tree.opcodes;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.tree.Expression;

/**
 * @author matt123337
 */
public class IOExpression implements Expression {

    private boolean input;

    public IOExpression(boolean input){
        this.input = input;
    }

    public boolean isInput(){
        return input;
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[input=" + input + "]";
    }

    @Override
    public void visit(BrainFuckVisitor visitor) {
        visitor.visitIO(input);
    }
}
