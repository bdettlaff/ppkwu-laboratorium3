package com.example.zadanie3.endpoint;

import com.example.zadanie3.service.Ical;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CalendarEndpoint {

    @GetMapping("calendar/{month}")
    public ResponseEntity<Resource> getCalendar(@PathVariable int month) throws IOException {
        Document doc = Jsoup.connect("http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=2019&miesiac=" + month +"&lang=1").get();
        Elements html = doc.select("a.active");
        Elements events = doc.select ("div.InnerBox");

        List<String> dates = new ArrayList<>();
        List<String> eventName = new ArrayList<>();

        for (Element e : html) {
            dates.add(e.text());
        }

        for (Element e : events) {
            eventName.add(e.text());
        }

        Ical ical = new Ical();
        ical.write(dates,month,eventName);

        File file = new File("calendar"+ month + ".ics");
        Resource fileSystemResource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/calendar"))
                .body(fileSystemResource);
    }
}
