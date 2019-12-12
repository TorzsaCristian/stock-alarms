package com.torzsa.stockalarms.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Date;

public class Utils {

    public static Date convertTimestampToDate(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        return new Date(ts.getTime());
    }

    public static String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }


}
