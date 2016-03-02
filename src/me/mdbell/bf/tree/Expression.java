package me.mdbell.bf.tree;

import me.mdbell.bf.BrainFuckVisitor;

/**
 * @author matt123337
 */
public interface Expression {

    void visit(BrainFuckVisitor visitor);
}
