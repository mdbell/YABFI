package me.mdbell.bf.bc;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.tree.BlockOptimiser;
import me.mdbell.bf.util.InputSource;
import me.mdbell.bf.util.SourceReader;
import me.mdbell.bf.util.VerifyingVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

/**
 * @author matt123337
 */
public class Entry {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Please provide a source file and a class name!");
            return;
        }
        File f = new File(args[0]);

        if (!f.exists()) {
            System.err.printf("File '%s' doesn't exist!%n",args[0]);
            return;
        }

        ByteBuffer buffer = ByteBuffer.wrap(Files.readAllBytes(f.toPath()));
        SourceReader reader = new SourceReader(new InputSource(buffer));
        BlockOptimiser b = new BlockOptimiser();
        BrainFuckVisitor visitor = new VerifyingVisitor(b);
        reader.accept(visitor);

        String classname = args[1];
        ClassNode node = new ClassNode();
        //CheckClassAdapter adapter = new CheckClassAdapter(node);
        BrainFuckClassAdapter v = new BrainFuckClassAdapter(classname,30_000,node);

        b.accept(v);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(cw);
        f = new File(classname + ".class");
        Files.write(f.toPath(),cw.toByteArray());
    }
}
