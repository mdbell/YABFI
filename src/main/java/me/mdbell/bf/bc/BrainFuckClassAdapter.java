package me.mdbell.bf.bc;

import me.mdbell.bf.BrainFuckVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author matt123337
 */
public class BrainFuckClassAdapter implements BrainFuckVisitor, Opcodes {

    public static final String PARENT_CLASS = "java/lang/Object";

    public static final int GENERATED_CLASS_ACCESS = ACC_PUBLIC | ACC_FINAL;

    public static final int GENERATED_METHOD_ACCESS = ACC_PRIVATE | ACC_STATIC;

    public static final String METHOD_PREFIX = "_";

    public static final int TAPE_LOCAL = 0;
    public static final int POINTER_LOCAL = 1;

    private String className;
    private int tapeSize;

    private Deque<MethodVisitor> visitors = new LinkedList<>();
    private MethodVisitor mv;
    private int methodCounter = 0;
    
    private ClassVisitor cv;

    public BrainFuckClassAdapter(String className, int tapeSize, ClassVisitor cv) {
        this.className = className;
        this.tapeSize = tapeSize;
        this.cv = cv;
    }

    @Override
    public void visitStart() {
        init();
    }

    @Override
    public void visitShift(int shift) {
        mv.visitIincInsn(POINTER_LOCAL, shift);
    }

    @Override
    public void visitInc(int inc) {
        pushTapeReference();
        pushPointer();
        mv.visitInsn(DUP2);
        mv.visitInsn(BALOAD);
        int i = Math.abs(inc);
        mv.visitIntInsn(i > 255 ? SIPUSH : BIPUSH, i);
        if (inc >= 0) {
            mv.visitInsn(IADD);
        } else {
            mv.visitInsn(ISUB);
        }
        mv.visitInsn(I2B);
        mv.visitInsn(BASTORE);
    }

    @Override
    public void visitIO(boolean input) {
        String owner = "java/lang/System";
        String name = "in";
        String type = "java/io/InputStream";

        String methodName = "read";
        String methodDesc = "()I";
        if (!input) {
            name = "out";
            type = "java/io/PrintStream";

            methodName = "write";
            methodDesc = "(I)V";
        }

        if (input) {
            pushTapeReference();
            pushPointer();
        }

        mv.visitFieldInsn(GETSTATIC, owner, name, "L" + type + ";");
        if (!input) {
            pushDereference();
        }

        mv.visitMethodInsn(INVOKEVIRTUAL, type, methodName, methodDesc, false);

        if (input) {
            mv.visitInsn(BASTORE);
        }
    }

    @Override
    public void visitJump(boolean start) {
        if (start) {
            Label cmp = new Label();
            Label entry = new Label();
            mv.visitJumpInsn(GOTO, cmp);
            mv.visitLabel(entry);
            pushTapeReference();
            pushPointer();
            mv.visitMethodInsn(INVOKESTATIC, className, METHOD_PREFIX + methodCounter, "([BI)I", false);
            mv.visitVarInsn(ISTORE, POINTER_LOCAL);
            mv.visitLabel(cmp);
            mv.visitInsn(ICONST_0);
            pushDereference();
            mv.visitJumpInsn(IF_ICMPNE, entry);
            createNewMethodAndPush();
        } else {
            close();
        }
    }

    private void pushTapeReference() {
        mv.visitVarInsn(ALOAD, TAPE_LOCAL);
    }

    @Override
    public void visitEnd() {
        while (mv != null) {
            close();
        }
        cv.visitEnd();
    }

    private void init() {
        cv.visit(V1_7, GENERATED_CLASS_ACCESS, className, null, PARENT_CLASS, null);
        MethodVisitor mv = cv.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, PARENT_CLASS, "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);

        mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitIntInsn(SIPUSH, tapeSize);
        mv.visitIntInsn(NEWARRAY, T_BYTE);
        mv.visitInsn(ICONST_0);
        mv.visitMethodInsn(INVOKESTATIC, className, METHOD_PREFIX + methodCounter, "([BI)I", false);
        mv.visitInsn(POP);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 2);

        createNewMethodAndPush(); // create the entry method
    }

    private void close() {
        pushPointer();
        mv.visitInsn(IRETURN);
        mv.visitMaxs(4, 2);
        mv = visitors.isEmpty() ? null : visitors.pop();
    }

    public void pushPointer() {
        mv.visitVarInsn(ILOAD, POINTER_LOCAL);
    }

    private void pushDereference() {
        pushTapeReference();
        pushPointer();
        mv.visitInsn(BALOAD);
    }

    private void createNewMethodAndPush() {
        if(this.mv != null){
            visitors.push(mv);
        }
        mv = cv.visitMethod(GENERATED_METHOD_ACCESS, METHOD_PREFIX + methodCounter++, "([BI)I", null, null);
        mv.visitCode();
    }
}
