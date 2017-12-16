package source.algorytm;

import java.io.*;
import java.nio.file.Files;

public class RWfiles {

    private static String KODOWANIE = "ISO-8859-1";

    public String readFile(File file){
        try {
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
        Writer writer = null;
        try {
            if (!outPutFile.exists()) {
                outPutFile.createNewFile();
            }
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
