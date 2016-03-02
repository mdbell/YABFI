package me.mdbell.bf.util;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.Opcodes;

/**
 * @author matt123337
 */
public class SourceReader implements Opcodes {

    public static final OpcodeVisitor DEFAULT_VISITOR = (v, c) -> {
    };
    private InputSource source;
    private OpcodeVisitor[] opcodes = new OpcodeVisitor[Character.BYTES * Byte.MAX_VALUE];

    public SourceReader(InputSource source) {
        this.source = source;
        initOpcodes();
    }

    protected void initOpcodes() {
        opcodes[INC_CHAR] = (v, c) -> v.visitInc(1);
        opcodes[DEC_CHAR] = (v, c) -> v.visitInc(-1);
        opcodes[START_BLOCK] = (v, c) -> v.visitJump(true);
        opcodes[END_BLOCK] = (v, c) -> v.visitJump(false);
        opcodes[RIGHT_SHIFT] = (v, c) -> v.visitShift(1);
        opcodes[LEFT_SHIFT] = (v, c) -> v.visitShift(-1);
        opcodes[INPUT_CHAR] = (v, c) -> v.visitIO(true);
        opcodes[OUTPUT_CHAR] = (v, c) -> v.visitIO(false);
    }

    public void accept(BrainFuckVisitor visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("null visitor!");
        }
        visitor.visitStart();
        while (!source.isEmpty()) {
            char c = source.get();
            OpcodeVisitor v = opcodes[c];
            if (opcodes[c] == null) {
                v = DEFAULT_VISITOR;
            }

            v.visit(visitor, c);
        }
        visitor.visitEnd();
    }

    public interface OpcodeVisitor {
        void visit(BrainFuckVisitor visitor, char c);
    }
}
