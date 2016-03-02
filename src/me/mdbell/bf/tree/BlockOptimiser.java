package me.mdbell.bf.tree;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.tree.opcodes.IOExpression;
import me.mdbell.bf.tree.opcodes.IncrementExpression;
import me.mdbell.bf.tree.opcodes.JumpExpression;
import me.mdbell.bf.tree.opcodes.ShiftExpression;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author matt123337
 */
public class BlockOptimiser implements BrainFuckVisitor {

    private final List<Expression> expressionList = new LinkedList<>();

    @Override
    public void visitStart() {

    }

    @Override
    public void visitShift(int shift) {
        Expression last = getLastExpression();
        if(last instanceof ShiftExpression){
            ShiftExpression expr = (ShiftExpression)last;
            expr.shift(shift);
            if(expr.getShift() == 0){
                expressionList.remove(expressionList.size() - 1);
                return;
            }
        }else {
            expressionList.add(new ShiftExpression(shift));
        }
    }

    @Override
    public void visitInc(int inc) {
        Expression last = getLastExpression();
        if(last instanceof IncrementExpression){
            IncrementExpression expr = (IncrementExpression)last;
            expr.increment(inc);
            if(expr.getIncrement() == 0){
                expressionList.remove(expressionList.size() - 1);
            }
        }else {
            expressionList.add(new IncrementExpression(inc));
        }
    }

    @Override
    public void visitIO(boolean input) {
        expressionList.add(new IOExpression(input));
    }

    @Override
    public void visitJump(boolean start) {
        expressionList.add(new JumpExpression(start));
    }

    @Override
    public void visitEnd() {

    }

    public void accept(BrainFuckVisitor visitor){
        visitor.visitStart();
        Iterator<Expression> itr = expressionList.iterator();
        while(itr.hasNext()){
            itr.next().visit(visitor);
        }
        visitor.visitEnd();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        Iterator<Expression> itr = expressionList.iterator();

        while(itr.hasNext()){
            builder.append(itr.next());
            if(itr.hasNext()){
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    private Expression getLastExpression() {
        return expressionList.isEmpty() ? null : expressionList.get(expressionList.size() - 1);
    }
}
