package me.mdbell.bf.util;

import java.io.PrintStream;

/**
 * @author matt123337
 */
public class FormattedPrintingVisitor extends PrintingVisitor {
    private int indent = 0;

    public FormattedPrintingVisitor(PrintStream out) {
        super(out);
    }

    @Override
    public void visitJump(boolean start) {
        if (start) {
            indent();
            indent++;
        } else {
            indent--;
            indent();
        }

        super.visitJump(start);
        //create a new line, and indent it
        indent();
    }

    private void indent() {
        out.println();
        for (int i = 0; i < indent; i++) {
            out.print(INDENT_CHAR);
        }
    }
}
