package com.example.linenotify;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author 陳金昌 Chris Chen
 * @version 1.0 2021/8/18 11:30 AM
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //AZ
//        Document doc = Jsoup.connect("https://reg.ntuh.gov.tw/WebAdministration/VaccineClinicReg.aspx?Hosp=T0&Reg=&cliniccode=03").validateTLSCertificates(false).get();
        //莫德納
        Document doc = Jsoup.connect("https://reg.ntuh.gov.tw/WebAdministration/VaccineClinicReg.aspx?Hosp=T0&Reg=&cliniccode=06").validateTLSCertificates(false).get();
//        Elements select = doc.select("#pnl_ClinicTable > table > tbody > tr:eq(2) > td > span > span > label");
        Elements select = doc.select("#pnl_ClinicTable > table > tbody > tr:eq(2) > td > span > label");
        String[] s = select.text().split(" ");
        for (String content : s) {
            System.out.println(content);
            if(content.contains("額滿") || content.contains("已截止掛號") || content.isEmpty()){
                System.out.println("滿了");
            }else {
                System.out.println("沒滿,趕快預約");
            }
        }
    }
}
