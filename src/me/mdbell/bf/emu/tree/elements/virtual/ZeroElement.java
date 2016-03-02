package me.mdbell.bf.emu.tree.elements.virtual;

import me.mdbell.bf.emu.InterpreterContext;
import me.mdbell.bf.emu.tree.Element;
import me.mdbell.bf.emu.tree.elements.AbstractElement;

/**
 * @author matt123337
 */
public class ZeroElement extends AbstractElement{
    public ZeroElement(Element parent) {
        super(parent);
    }

    @Override
    public void exec(InterpreterContext emu) {
        emu.setValue(0);
    }
}
