package me.mdbell.bf.emu.tree.elements;

import me.mdbell.bf.emu.InterpreterContext;
import me.mdbell.bf.emu.tree.Element;
import me.mdbell.bf.emu.tree.ElementList;

/**
 * @author matt123337
 */
public class LoopElement extends AbstractElement implements ElementList {

    int pointer = 0;
    private Element[] elements = new Element[1];

    public LoopElement(Element parent) {
        super(parent);
    }


    @Override
    public Element[] getElements() {
        return elements;
    }

    @Override
    public void add(Element e) {
        if (pointer == elements.length) {
            Element[] tmp = new Element[elements.length * 2];
            System.arraycopy(elements, 0, tmp, 0, elements.length);
            elements = tmp;
        }
        elements[pointer++] = e;
    }

    @Override
    public void exec(InterpreterContext emu) {
        if (emu.getValue() == 0) {
            return;
        }
        run(emu);
    }

    private void run(InterpreterContext emu) {
        do {
            for (int i = 0; i < pointer; i++) {
                elements[i].exec(emu);
            }
        } while (emu.getValue() != 0);
    }
}
