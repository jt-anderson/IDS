import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class FileHashMaker {
    public static void writeCSVHashTable(String fileName, Set<Path> paths, boolean recursive) throws Exception{
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(fileName,false));
        Consumer<? super Path> hashWriteConsumer = (Consumer<Path>) path -> {
            try {
                System.out.println("Hashing " + path.toString());
                csvWriter.append(DigestUtils.sha256Hex(new FileInputStream(path.toString()))).append(",").append(path.toString()).append("\n");
            } catch (IOException e) {
                System.out.println(e);
            }
        };

        for (Path path: paths) {
            if(path.toFile().isDirectory()){
                if(recursive) Files.walk(path)
                        .filter(Files::isRegularFile)
                        .forEach(hashWriteConsumer);
                else Files.walk(path,1)
                        .filter(Files::isRegularFile)
                        .forEach(hashWriteConsumer);
            }
            else {
                hashWriteConsumer.accept(path);
            }
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
