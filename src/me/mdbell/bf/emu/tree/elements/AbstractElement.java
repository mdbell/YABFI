package me.mdbell.bf.emu.tree.elements;

import me.mdbell.bf.emu.tree.Element;

/**
 * @author matt123337
 */
public abstract class AbstractElement implements Element {

    private Element parent;

    public AbstractElement(Element parent){
        this.parent = parent;
    }

    @Override
    public Element getParent() {
        return parent;
    }

}
