package com.sii.conferention.management.system.services;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TimeService {

    public Date getTemporalTime(int hourOfDay, int minuteOfDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minuteOfDay);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
