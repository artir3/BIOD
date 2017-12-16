package source.algorytm;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class RWfiles {

    private static String KODOWANIE = "windows-1250";

    public String readFile(File file){
        try {
//            return new Scanner(file,KODOWANIE).toString();
                return new String ( Files.readAllBytes(file.toPath()),KODOWANIE );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void writeFile(StringBuilder os, File outPutFile, File inputFile){
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(outPutFile.getAbsolutePath());

            if (!outPutFile.exists()) {
                outPutFile.createNewFile();
            }

            fop.write(os.toString().getBytes());
            fop.flush();
            fop.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//      inputFile.delete();
    }

    public void writeFile2(StringBuilder os, File outPutFile, File inputFile){
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(outPutFile), KODOWANIE);
            writer.write(os.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//      inputFile.delete();
    }
}
