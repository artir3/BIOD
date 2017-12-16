package source.algorytm;

import java.io.*;

public class RWfiles {

    public InputStream readFile(File file ){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fis;
    }


    public void writeFile(byte[] os, File outPutFile, File inputFile){
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(outPutFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!outPutFile.exists()) {
                try {
                    outPutFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        try {
            fop.write(os);
            fop.flush();
            fop.close();
            inputFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
