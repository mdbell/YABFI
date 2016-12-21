package me.mdbell.bf.emu.tree;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.emu.InterpreterContext;
import me.mdbell.bf.emu.tree.elements.*;
import me.mdbell.bf.emu.tree.elements.virtual.ZeroElement;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author matt123337
 */
public class RootElement implements ElementList, BrainFuckVisitor {

    private Element[] elements = new Element[1];
    int pointer = 0;

    private final Deque<ElementList> nodes = new LinkedList<>();

    @Override
    public Element[] getElements() {
        return elements;
    }

    @Override
    public void add(Element e) {
        if(pointer == elements.length){
            Element[] tmp = new Element[elements.length * 2];
            System.arraycopy(elements,0,tmp,0,elements.length);
            elements = tmp;
        }
        elements[pointer++] = e;
    }

    @Override
    public Element getParent() {
        return null; // always the root element
    }

    @Override
    public void exec(InterpreterContext emu) {
        for(int i = 0; i < pointer;i++){
            elements[i].exec(emu);
        }
    }

    @Override
    public void visitStart() {
        nodes.push(this);
    }

    @Override
    public void visitShift(int shift) {
        ElementList target = nodes.peek();
        target.add(new ShiftElement(target, shift));
    }

    @Override
    public void visitInc(int inc) {
        ElementList target = nodes.peek();
        target.add(new IncrementElement(target, inc));
    }

    @Override
    public void visitIO(boolean input) {
        ElementList target = nodes.peek();
        target.add(input ? new InputElement(target) : new OutputElement(target));
    }

    @Override
    public void visitJump(boolean start) {
        if (start) {
            LoopElement element = new LoopElement(nodes.peek());
            nodes.push(element);
        } else {
            Element element = nodes.pop();
            if(element instanceof LoopElement){
                LoopElement loop = (LoopElement)element;
                Element[] elms = loop.getElements();
                if(elms.length == 1 && elms[0] instanceof IncrementElement){
                    if(((IncrementElement)elms[0]).getInc() == -1){
                        element = new ZeroElement(nodes.peek());
                    }
                }
            }
            nodes.peek().add(element);
        }
    }

    @Override
    public void visitEnd() {
        nodes.clear();
    }
}
