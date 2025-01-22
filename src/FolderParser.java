/**
 * Author Names : Parth Patel, Vishrut Shah, Jui Desai
 *
 * Contact info :
 * Parth: parthashokbhai.patel@student.csulb.edu
 * Vishrut: vishrutshah15@gmail.com
 * Jui: jui1995@yahoo.in
 *
 * Description :  Folder Parser class parses all the folders and files inside the Source folder of project path given by user command.
 * It Create a copy of the project tree folder hierarchy in the target repo folder.
 * parseFIle method will copy each project tree file into its own new leaf folder. The new leaf folder will be given the
 * file's name, including its extension
 * getFileName method will return a code name(artifact ID) of the copied file's new file name with the file's original
 * extension unchanged.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by parth on 2/25/2017.
 */
public class FolderParser {
    private Queue<File> directories = new LinkedList<>();
    private static final int WEIGHTS[] = {1, 3, 11, 17};

    private String src;
    private String dst;
    private File srcFile;
    private File targetFile;
    private String srcFileName;
    
    public FolderParser(String src, String dest) {
        this.src = src;
        this.dst = dest;
        this.srcFile = new File(src);
    }

    public void parse() {
        directories.add(srcFile);
        while (!directories.isEmpty()) {
            parseFolder(directories.poll());
        }
    }

    private void parseFolder(File sourcefile) {
        for (File f1 : sourcefile.listFiles()) {
            if (f1.isDirectory()) {
                directories.add(f1);
            } else {
                srcFileName = f1.getName() + ",";
                parseFile(f1);
            }
        }
    }

    private void parseFile(File f1) {
        String path = f1.getAbsolutePath();
        String fileName = getFileName(f1);

        path = path.replace(src, dst);
        path += File.separator;
        File dPath = new File(path);
        dPath.mkdirs();

        path += fileName;
        targetFile = new File(path);
        Source.content += targetFile.getName() + ",";
        Source.content += srcFileName;
        Source.content += targetFile.getAbsolutePath().replace(Source.cmdElements[2], "") + "\r\n";

        try {
            if (!targetFile.exists()) {
                Files.copy(Paths.get(f1.getAbsolutePath()), Paths.get(path));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(File f1) {
        long artifactId = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(f1))) {
            for (int i = 0, j; (j = bufferedReader.read()) > 0; i++) {
                artifactId += j * WEIGHTS[i % 4];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String f1Name = f1.getName();
        int index = f1Name.lastIndexOf(".");
        String ext = "";

        if (index > 0) {
            ext = f1Name.substring(index, f1Name.length());
        }
        return artifactId + "." + f1.length() + ext;
    }
}
