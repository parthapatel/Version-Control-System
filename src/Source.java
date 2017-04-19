/**
 * Author Names : Parth Patel, Vishrut Shah, Jui Desai
 *
 * Contact info :
 * Parth: parthashokbhai.patel@student.csulb.edu
 * Vishrut: vishrutshah15@gmail.com
 * Jui: jui1995@yahoo.in
 *
 * Description :  The main method of Source class gets the source tree path and the target repo folder path from the user.
 * createManifest method adds an activity folder to the repository and creates a manifest file for the repository
 * creation (a record of the user command's activity).
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by parth on 2/25/2017.
 */
public class Source {

    static String path;
    static String content;
    static String cmd;
    static String[] cmdElements;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Command");
        cmd = sc.nextLine();
        cmdElements = cmd.split("\\s+");
        System.out.println(cmdElements[1]);
        FolderParser folderParser = new FolderParser(cmdElements[1], cmdElements[2]);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        content = "PVJ-1" + "\r\n";
        content += "Current Date: " + ft.format(dNow).toString() + "\r\n";
        content += cmd + "\r\n";
        content += cmdElements[1] + "\r\n" + cmdElements[2] + "\r\n";
        folderParser.parse();
        createManifest(content);
    }

    public static void createManifest(String content) throws IOException {
        SimpleDateFormat ft2 = new SimpleDateFormat("yyyyMMdd_hhmm");
        File file = new File("F:\\Activity");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        Date date = new Date();
        String fileNmae = "Manifest_" + ft2.format(date).toString() + ".txt";
        System.out.println(fileNmae);
        path = cmdElements[2] + File.separator + "Activity" + File.separator + fileNmae;
        File f = new File(path);
        f.getParentFile().mkdirs();
        f.createNewFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write(content);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
