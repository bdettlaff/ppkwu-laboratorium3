package com.example.zadanie3.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Ical {

    private String version = "VERSION:2.0\r\n";
    private String prodid = "PRODID:-//bobbin v0.1//NONSGML iCal Writer//EN\r\n";
    private String calBegin = "BEGIN:VCALENDAR\r\n";
    private String calEnd = "END:VCALENDAR\r\n";
    private String eventBegin = "BEGIN:VEVENT\r\n";
    private String eventEnd = "END:VEVENT\r\n";
    private String calscale = "CALSCALE:GREGORIAN\r\n";
    private String method = "METHOD:PUBLISH\r\n";
    private String dateStart = "DTSTART:2019";
    private String dateEnd = "DTEND:2019";
    private String summary = "SUMMARY:";
    private String newLine = "\r\n";


    public void write(List<String> dates, int month, List<String> eventName) {
        StringBuilder builder = new StringBuilder();
        builder.append("calendar");
        builder.append(month);
        builder.append(".ics");

        try {
            File file = new File(builder.toString());
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(calBegin);
            bw.write(version);
            bw.write(prodid);
            bw.write(calscale);
            bw.write(method);

            for (int i = 0; i < dates.size(); i++) {
                bw.write(eventBegin);
                if(dates.get(i).length()==1){
                    bw.write(dateStart + month + "0" + dates.get(i) + newLine);
                    bw.write(dateEnd + month + "0" + dates.get(i) + newLine);
                }
                else{
                    bw.write(dateStart + month + dates.get(i) + newLine);
                    bw.write(dateEnd + month + dates.get(i) + newLine);
                }
                bw.write(summary + eventName.get(i) + newLine);
                bw.write(eventEnd);
            }

            bw.write(calEnd);
            bw.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
