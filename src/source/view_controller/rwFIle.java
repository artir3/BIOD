package source.view_controller;

import java.io.*;

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


    public void writeFile(StringBuilder os, File outPutFile, File inputFile){
        try (FileOutputStream fop = new FileOutputStream(
                outPutFile.getAbsolutePath() + "/" + inputFile.getName() + ".txt")) {

            if (!outPutFile.exists()) {
                outPutFile.createNewFile();
            }

            fop.write(os.toString().getBytes());
            fop.flush();
            fop.close();

            inputFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
