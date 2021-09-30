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

    private static CsvReader reader = null;

    private List<Complaint> csvRows;
    private Map<Integer, Integer> offenseTypeTotals;
    private String filePath;

    private CsvReader(String path) {
        this.filePath = path;
        csvRows = readFromCsv(filePath);
        offenseTypeTotals = populateOffenseTypes();
    }

    public static CsvReader getInstance(String path){
        if(reader == null){
            return new CsvReader(path);
        }
        return reader;
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

    public void addRow(String cmplntNum, String kyCd) throws NumberFormatException {
        long complaintNumber = -1L;
        int ky = -1;

        try {
            complaintNumber = Long.parseLong(cmplntNum);
            ky = Integer.parseInt(kyCd);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }

        if(complaintNumber != -1 && ky != -1) {
            Complaint newComplaint = new Complaint();
            newComplaint.setCmplntNum(complaintNumber);
            newComplaint.setKyCd(ky);

            csvRows.add(newComplaint);

            writeToCsv();

            if (offenseTypeTotals.containsKey(ky)) {
                offenseTypeTotals.put(ky, offenseTypeTotals.get(ky) + 1);
            } else {
                offenseTypeTotals.put(ky, 1);
            }
        }
    }

    public boolean deleteOffense(Long id) {
        int kyCd = -1;
        boolean removed = false;

        for (int i = 0; i < csvRows.size(); i++) {
            if(csvRows.get(i).getCmplntNum() == id){
                removed = true;
                kyCd = csvRows.get(i).getKyCd();
                csvRows.remove(i);
                break;
            }
        }

        if(removed) {
            writeToCsv();

            if(offenseTypeTotals.containsKey(kyCd)){
                offenseTypeTotals.put(kyCd, offenseTypeTotals.get(kyCd) - 1);
                if(offenseTypeTotals.get(kyCd) == 0){
                    offenseTypeTotals.remove(kyCd);
                }
            }
        }

        return removed;
    }

    private synchronized void writeToCsv() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath))) {
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
