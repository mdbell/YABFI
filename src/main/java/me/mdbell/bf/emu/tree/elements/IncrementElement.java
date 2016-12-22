package me.mdbell.bf.emu.tree.elements;

import me.mdbell.bf.emu.InterpreterContext;
import me.mdbell.bf.emu.tree.Element;

/**
 * @author matt123337
 */
public class IncrementElement extends AbstractElement{

    private int inc;

    public IncrementElement(Element parent,int inc) {
        super(parent);
        this.inc = inc;
    }

    @Override
    public void exec(InterpreterContext emu) {
        emu.increment(inc);
    }

    public int getInc() {
        return inc;
    }
}
