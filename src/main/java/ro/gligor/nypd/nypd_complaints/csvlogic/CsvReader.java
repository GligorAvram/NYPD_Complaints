package ro.gligor.nypd.nypd_complaints.csvlogic;


import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ro.gligor.nypd.nypd_complaints.entities.Complaint;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    private List<Complaint> csvRows;
    private Map<Integer, Integer> offenseTypeTotals;
    private String filePath;
    private String[] headers;

    public CsvReader(String path) {
        this.filePath = path;
        csvRows = readFromCsv(filePath);
        offenseTypeTotals = populateOffenseTypes();
    }

    private Map<Integer, Integer> populateOffenseTypes() {
        Map<Integer, Integer> offenses = new HashMap<>();
        csvRows.forEach(offense -> {
            if(offenses.containsKey(offense.getKyCd())){
                offenses.put(offense.getKyCd(), offenses.get(offense.getKyCd()) + 1);
            }
            else{
                offenses.put(offense.getKyCd(), 1);
            }
        });

        return offenses;
    }

    public int getEventsNumber() {
        return csvRows.size();
    }

    private List<Complaint> readFromCsv(String path) {
        List<Complaint> complaints = null;
        try {
            complaints = new CsvToBeanBuilder(new FileReader(path))
                .withType(Complaint.class).build().parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return complaints;
    }

    public Map<Integer, Integer> getOffenses() {
        return offenseTypeTotals;
    }

    public void addRow(String cmplntNum, String kyCd) {
        long complaintNumber = Long.parseLong(cmplntNum);
        int ky = Integer.parseInt(kyCd);

        Complaint newComplaint = new Complaint();
        newComplaint.setCmplntNum(complaintNumber);
        newComplaint.setKyCd(ky);

        csvRows.add(newComplaint);

        if(offenseTypeTotals.containsKey(ky)){
            offenseTypeTotals.put(ky, offenseTypeTotals.get(ky) + 1);
        }
        else{
            offenseTypeTotals.put(ky, 1);
        }

        try (Writer writer = Files.newBufferedWriter(Paths.get("src\\sample.csv"))) {
            StatefulBeanToCsv<Complaint> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .build();

            beanToCsv.write(csvRows);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
}
