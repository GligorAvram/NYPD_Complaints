package ro.gligor.nypd.nypd_complaints.controllers;


import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.gligor.nypd.nypd_complaints.csvlogic.CsvReader;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

@RestController
public class Controller {
    @Value("${csv.path}")
    private String csvPath;

    private CsvReader csvHandler;

    @PostConstruct
    private void getPath(){
        csvHandler  = CsvReader.getInstance(csvPath);
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("dataset/stats/total")
    public Map getComplaints(){
        return Collections.singletonMap("total_events", csvHandler.getEventsNumber());
    }

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("dataset/stats/offenses")
    public Map<Integer, Integer> getOffenses(){
        return csvHandler.getOffenses();
    }

    @PostMapping(value = "/dataset", consumes = "application/json;charset=UTF-8")
    public void addOffense(@RequestBody JSONObject payload){
        if(payload.containsKey("CMPLNT_NUM") && payload.containsKey("KY_CD")){
            csvHandler.addRow(payload.get("CMPLNT_NUM").toString(), payload.get("KY_CD").toString());
        }
    }

    @DeleteMapping(value = "/dataset/{id}")
    public boolean deleteMapping(@PathVariable("id") Long id){
        return csvHandler.deleteOffense(id);
    }
}
