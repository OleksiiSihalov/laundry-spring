package com.sihalov.laundry.scheduler;

import net.minidev.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
@CrossOrigin(origins = "http://localhost:3000")
public class LaundryScheduler {


    private boolean[] washers1 = new boolean[2];
    private boolean[] dryers1 = new boolean[2];
    private String availability = "0/0";
    private long checkTime;

    @CrossOrigin
    @GetMapping
    public JSONObject getAvailability() {
        Map<String, String> response = new HashMap<>();
        response.put("checkTime", String.valueOf(checkTime));
        response.put("availability", availability);
        return new JSONObject(response);
    }

    @Scheduled(fixedRate = 2700000)
    public void UpdateAvailability() {
        int washersCount = 0;
        int dryersCount = 0;

        for (int i = 0; i < washers1.length; i++) {
            double dif = Math.random();
            if (dif - 0.5 > 0) {
                washers1[i] = !washers1[i];

            } else {
                dryers1[i] = !dryers1[i];
            }
            if (washers1[i]) {
                washersCount++;
            }
            if (dryers1[i]) {
                dryersCount++;
            }
        }
        availability = washersCount + "/" + dryersCount;
        checkTime = new Date().getTime();
    }
}
