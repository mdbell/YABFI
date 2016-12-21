package me.mdbell.bf.emu;

import java.io.IOException;

/**
 * @author matt123337
 */
public class InterpreterContext {

    public static final int DEFAULT_TAPE_SIZE = 30_000;
    private byte[] tape;

    private int pointer;


    public InterpreterContext(int size) {
        tape = new byte[size];
        pointer = 0;
    }

    public InterpreterContext() {
        this(DEFAULT_TAPE_SIZE);
    }

    public void pointerIncrement(int i) {
        pointer += i;
    }

    public int getValue() {
        int i = tape[pointer] & 0xFF;
        return i;
    }

    public void setValue(int value) {
        tape[pointer] = (byte)value;
    }

    public void increment(int i) {
        int value = tape[pointer] + i;
        setValue(value);
    }

    public void write(char value) {
        System.out.write(value);
    }

    public int read() {
        try {
            return System.in.read();
        } catch (IOException e) {
            return 0;
        }
    }
}
