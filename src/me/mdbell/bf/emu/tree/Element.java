package me.mdbell.bf.emu.tree;

import me.mdbell.bf.emu.InterpreterContext;

/**
 * @author matt123337
 */
public interface Element {

    Element getParent();

    void exec(InterpreterContext emu);
}
