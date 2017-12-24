package me.mdbell.bf.lol;

import me.mdbell.bf.tree.BlockOptimiser;
import me.mdbell.bf.util.CountingVisitor;
import me.mdbell.bf.util.InputSource;
import me.mdbell.bf.util.NullVisitor;
import me.mdbell.bf.util.SourceReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Generate {

    public static void main(String[] args) throws IOException {
        if(args.length != 2) {
            System.err.println("Invalid args! Please provide an input filename and an output filename!");
            return;
        }
        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        if(!Files.exists(input)) {
            System.err.println("Input file doesn't exist!");
            return;
        }

        if(!Files.isRegularFile(input)) {
            System.err.println("Input file isn't a file?");
            return;
        }

        ByteBuffer buffer = ByteBuffer.wrap(Files.readAllBytes(input));
        InputSource source = new InputSource(buffer);

        SourceReader reader = new SourceReader(source);

        BlockOptimiser block = new BlockOptimiser();
        reader.accept(block);

        CountingVisitor counter = new CountingVisitor(new NullVisitor());

        block.accept(counter);

        System.out.println("Min number of pixels needed:" + counter.getCount());

        //compute rough image size
        int height = (int) Math.ceil(Math.sqrt(counter.getCount()));

        //The +2 is to account for the rotation on the left and right of each row
        int width = height + 2;

        System.out.printf("Image size: %dx%d%n", width, height);

        BufferedImage img = new BufferedImage(width, height,BufferedImage.TYPE_BYTE_INDEXED);
        BrainlollerWriter writer = new BrainlollerWriter(img);

        block.accept(writer);

        ImageIO.write(img,"PNG", output.toFile());
    }
}
