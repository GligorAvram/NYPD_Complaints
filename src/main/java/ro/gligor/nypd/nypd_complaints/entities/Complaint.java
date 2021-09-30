package ro.gligor.nypd.nypd_complaints.entities;

import com.opencsv.bean.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class Complaint extends CsvToBean {

    @CsvBindByName(column = "CMPLNT_NUM")
    private long cmplntNum;

    @CsvBindByName(column = "CMPLNT_FR_DT")
    private String cmpltFrDt;

    @CsvBindByName(column = "CMPLNT_FR_TM")
    private String cmpltFrTm;

    @CsvBindByName(column = "CMPLNT_TO_DT")
    private String cmpltToDt;

    @CsvBindByName(column = "CMPLNT_TO_TM")
    private String cmpltToTm;

    @CsvBindByName(column = "RPT_DT")
    private String rptDt;

    @CsvBindByName(column = "KY_CD")
    private int kyCd;

    @CsvBindByName(column = "OFNS_DESC")
    private String ofnsDesc;

    @CsvBindByName(column = "PD_CD")
    private String pdCd;

    @CsvBindByName(column = "PD_DESC")
    private String pdDesc;

    @CsvBindByName(column = "CRM_ATPT_CPTD_CD")
    private String crmAtptCptdCd;

    @CsvBindByName(column = "LAW_CAT_CD")
    private String lawCatCd;

    @CsvBindByName(column = "JURIS_DESC")
    private String jurisDesc;

    @CsvBindByName(column = "BORO_NM")
    private String boroMn;

    @CsvBindByName(column = "ADDR_PCT_CD")
    private String addrPctCd;

    @CsvBindByName(column = "LOC_OF_OCCUR_DESC")
    private String locOfOccurDesc;

    @CsvBindByName(column = "PREM_TYP_DESC")
    private String premTypDesc;

    @CsvBindByName(column = "PARKS_NM")
    private String parksNm;

    @CsvBindByName(column = "HADEVELOPT")
    private String hadevelopt;

    @CsvBindByName(column = "X_COORD_CD")
    private String xCoordCd;

    @CsvBindByName(column = "Y_COORD_CD")
    private String yCoordCd;

    @CsvBindByName(column = "Latitude")
    private String latitude;

    @CsvBindByName(column = "Longitude")
    private String longitude;

    public Complaint(){}
}
