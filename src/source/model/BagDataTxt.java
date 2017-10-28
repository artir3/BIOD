package source.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

public class BagDataTxt implements BagData {
    private static BagDataTxt instance = new BagDataTxt();
    private int totalWeight;
    private ObservableList<BagItem> bagListData;
    private File file;

    private BagDataTxt() {
        this.bagListData = FXCollections.observableArrayList();
        this.totalWeight = 0;
    }

    public static BagDataTxt getInstance() {
        return instance;
    }

    public ObservableList<BagItem> getBagList() {
        return bagListData;
    }

    public String getTotalWeightG() {
        return Integer.toString(totalWeight);
    }

    public String getTotalWeightKG() {
        return String.format("%.3f", ((double) totalWeight / 1000));
    }

    private void calculateTotalWeight() {
        totalWeight = 0;
        for (BagItem temp : bagListData) {
            totalWeight += temp.getTotalWeight();
        }
    }

    @Override
    public void editItem(BagItem oldBagItem, String name, String weight, String amount, String note) {
        bagListData.get(bagListData.indexOf(oldBagItem)).editBody(name, weight, amount, note);
    }

    @Override
    public void addItem(BagItem bagItem) {
        bagListData.add(bagItem);
        totalWeight += bagItem.getTotalWeight();
    }

    @Override
    public void addItem(String name, String weight, String amount, String note) {
        bagListData.add(new BagItem(name, weight, amount, note));
        totalWeight += (Integer.parseInt(amount) * Integer.parseInt(weight));
    }
    //    @Override
//    public void addItem(String name, String weight, String amount, String note) {
//        bagListData.add(new BagItem
//                .Builder(name)
//                .weight(weight)
//                .amount(amount)
//                .note(note)
//                .build());
//
//    }

    @Override
    public void deleteItem(BagItem bagItem) {
        bagListData.remove(bagItem);
        totalWeight -= bagItem.getTotalWeight();
    }

    @Override
    public void saveItemData() throws IOException {
        Iterator<BagItem> iterator = bagListData.iterator();
        BufferedWriter bw = Files.newBufferedWriter(file.toPath());
        while (iterator.hasNext()) {
            BagItem item = iterator.next();
            bw.write(String.format("%s\t%s\t%s\t%s", item.getName(),
                    item.getWeight(),
                    item.getAmount(),
                    item.getNote().equals("") ? " " : item.getNote()));
            bw.newLine();
        }
    }

    @Override
    public void loadItemData() throws IOException {
        BufferedReader br = Files.newBufferedReader(file.toPath());
        String input;
        while ((input = br.readLine()) != null) {
            String[] itemsPieces = input.split("\t");
            String m_name = itemsPieces[0];
            String m_weight = itemsPieces[1];
            String m_amount = itemsPieces[2];
            String m_note = itemsPieces[3].equals(" ")?"":itemsPieces[3];
            addItem(m_name, m_weight, m_amount, m_note);
        }
        br.close();
        calculateTotalWeight();

    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean isFile() {
        return file != null;
    }
}
