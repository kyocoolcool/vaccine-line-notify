package com.example.linenotify;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陳金昌 Chris Chen
 * @version 1.0 2021/8/17 1:38 PM
 */
@Component
public class LineNotify {
    Logger logger=LoggerFactory.getLogger(LineNotify.class);

    @Scheduled(cron = "0 * * * * *")
    public void sendNotify() throws IOException {
         boolean flag = false;
        //AZ
//        Document doc = Jsoup.connect("https://reg.ntuh.gov.tw/WebAdministration/VaccineClinicReg.aspx?Hosp=T0&Reg=&cliniccode=03").validateTLSCertificates(false).get();
        //莫德納
        Document doc = Jsoup.connect("https://reg.ntuh.gov.tw/WebAdministration/VaccineClinicReg.aspx?Hosp=T0&Reg=&cliniccode=06").validateTLSCertificates(false).get();
//        Elements select = doc.select("#pnl_ClinicTable > table > tbody > tr:eq(2) > td > span > span > label");
        Elements select = doc.select("#pnl_ClinicTable > table > tbody > tr:eq(2) > td > span > label");
        String[] s = select.text().split(" ");
        for (String content : s) {
            logger.info(content);
            if(content.contains("額滿") || content.contains("已截止掛號") || content.isEmpty()){
                logger.info("滿了");
            }else {
                flag = true;
        }
     }
        if (flag) {
            send();
        }
    }

    public void send() {
        try {
            // Headers
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer JzN5liPru3zUnB3yJOoAId1brFfi8X5T4v8EidDDFss");
            HttpPostForm httpPostForm = new HttpPostForm("https://notify-api.line.me/api/notify", "utf-8", headers);
            // Add form field
            httpPostForm.addFormField("message", "沒滿,趕快預約");
            // Result
            String response = httpPostForm.finish();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Scheduled(cron = "*/30 * * * * *")
    public void sendNotifyTemplate() {
        try {
            // Headers
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer JzN5liPru3zUnB3yJOoAId1brFfi8X5T4v8EidDDFss");
            HttpPostForm httpPostForm = new HttpPostForm("https://notify-api.line.me/api/notify", "utf-8", headers);
            // Add form field
            httpPostForm.addFormField("message", "hi");
//            httpPostForm.addFormField("password", "test_psw");
            // Result
            String response = httpPostForm.finish();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
