package me.mdbell.bf.emu.tree;

import java.util.List;

/**
 * @author matt123337
 */
public interface ElementList extends Element{

    Element[] getElements();

    void add(Element e);
}
