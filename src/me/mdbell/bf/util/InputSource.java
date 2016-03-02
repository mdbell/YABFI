package me.mdbell.bf.util;

import java.nio.ByteBuffer;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author matt123337
 */
public class InputSource {

    private final Deque<Integer> marks = new LinkedList<>();
    private ByteBuffer buffer;

    public InputSource(String str){
        this(ByteBuffer.wrap(str.getBytes()));
    }

    public InputSource(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public void mark() {
        marks.push(buffer.position());
    }

    public void revert() {
        buffer.position(marks.pop());
    }

    public void rewind(){
        buffer.position(buffer.position() - Character.BYTES);
    }

    public boolean isEmpty(){
        return buffer.position() == buffer.capacity();
    }

    public char peek() {
        mark();
        char res = get();
        revert();
        return res;
    }

    public char get() {
        return (char) (buffer.get() & 0xFF);
    }

}
