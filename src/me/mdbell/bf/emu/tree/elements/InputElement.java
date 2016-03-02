package me.mdbell.bf.emu.tree.elements;

import me.mdbell.bf.emu.InterpreterContext;
import me.mdbell.bf.emu.tree.Element;

/**
 * @author matt123337
 */
public class InputElement extends AbstractElement{
    public InputElement(Element parent) {
        super(parent);
    }

    @Override
    public void exec(InterpreterContext emu) {
        emu.setValue(emu.read());
    }
}
