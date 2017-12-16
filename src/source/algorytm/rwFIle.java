package source.algorytm;

import java.io.*;
import java.nio.file.Files;

public class rwFIle {

    public InputStream readFile(File file ){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fis;
    }

    public byte[] readFiles(File file ){
        byte[] fis = null;
        try {
            fis = Files.readAllBytes(file.toPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fis;
    }

    public void writeFile(byte[] os, File outPutFile, File inputFile){
        try {
            Files.write(outPutFile.toPath(),os);
            inputFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void writeFile(StringBuilder os, File outPutFile, File inputFile){
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(outPutFile), "windows-1250");
            writer.write(os.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
            inputFile.delete();

    }
}
