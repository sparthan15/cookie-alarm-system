package com.charliegamboa.alarm.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {
    private final String name;
    private final List<Alarm> alarms;

    public static User create(String name) {
        User user = new User(name, new ArrayList<>());
        return user;
    }

    public Alarm setNewAlarm(LocalDateTime dateTime, String description) {
        if (Objects.isNull(dateTime)) {
            throw new RuntimeException("Alarm's date and time can not be null");
        }
        Alarm alarm = Alarm.setNew(dateTime, this, description);
        this.alarms.add(alarm);
        return alarm;
    }

    public void inactivate(Alarm... alarm) {
        this.alarms.removeAll(Arrays.asList(alarm));
    }
}
