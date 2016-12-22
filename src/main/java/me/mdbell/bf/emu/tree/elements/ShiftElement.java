package me.mdbell.bf.emu.tree.elements;

import me.mdbell.bf.emu.InterpreterContext;
import me.mdbell.bf.emu.tree.Element;

/**
 * @author matt123337
 */
public class ShiftElement extends AbstractElement{

    private int pointer;

    public ShiftElement(Element parent,int pointer) {
        super(parent);
        this.pointer = pointer;
    }

    @Override
    public void exec(InterpreterContext emu) {
        emu.pointerIncrement(pointer);
    }
}
