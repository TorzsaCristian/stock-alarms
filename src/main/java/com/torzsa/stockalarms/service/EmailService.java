package com.torzsa.stockalarms.service;

import com.torzsa.stockalarms.model.Alarm;

import java.util.List;

public interface EmailService {

    public void send(List<Alarm> alarms);
}
