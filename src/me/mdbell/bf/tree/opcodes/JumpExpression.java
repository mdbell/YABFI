package me.mdbell.bf.tree.opcodes;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.tree.Expression;

/**
 * @author matt123337
 */
public class JumpExpression implements Expression {

    private boolean start;

    public JumpExpression(boolean start){
        this.start = start;
    }

    public boolean isStart(){
        return start;
    }

    public String toString(){
        return getClass().getSimpleName() + "[start=" + start + "]";
    }

    @Override
    public void visit(BrainFuckVisitor visitor) {
        visitor.visitJump(start);
    }
}
