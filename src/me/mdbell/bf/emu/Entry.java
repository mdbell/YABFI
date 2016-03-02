package me.mdbell.bf.emu;

import me.mdbell.bf.BrainFuckVisitor;
import me.mdbell.bf.emu.tree.RootElement;
import me.mdbell.bf.tree.BlockOptimiser;
import me.mdbell.bf.util.InputSource;
import me.mdbell.bf.util.SourceReader;
import me.mdbell.bf.util.VerifyingVisitor;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

/**
 * @author matt123337
 */
public class Entry {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Please provide a source file");
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

        InterpreterContext emu = new InterpreterContext();
        RootElement root = new RootElement();
        b.accept(root);

        root.exec(emu);
    }
}
