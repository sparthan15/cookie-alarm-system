package com.charliegamboa.alarm.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class Alarm {
    @Getter
    private final int id;
    private AlarmStatus STATUS = AlarmStatus.ACTIVE;
    @Getter
    private final LocalDateTime dateTime;
    private final User owner;
    @Getter
    private final String description;

    private AlarmPeriod period;


    static Alarm setNew(LocalDateTime dateTime, User owner, String description) {
        //TODO: I don't like this
        if (Objects.isNull(owner)) {
            throw new RuntimeException("Alarm's owner can not be null");
        }
        if (Objects.isNull(dateTime)) {
            throw new RuntimeException("Alarm's date and time can not be null");
        }
        if (Objects.isNull(description)) {
            throw new RuntimeException("Alarm's description can not be null");
        }
        Alarm alarm = new Alarm(0, dateTime, owner, description);
        alarm.period = AlarmPeriod.ONCE;
        return alarm;
    }

    public boolean isActive() {
        return this.STATUS.equals(AlarmStatus.ACTIVE);
    }

    public void inactivate() {
        this.STATUS = AlarmStatus.INACTIVE;
    }

    public void repeat(AlarmPeriod period) {
        this.period = period;
    }

    public boolean isDaily() {
        return true;
    }

    public boolean runOnce() {
        return AlarmPeriod.ONCE.equals(this.period);
    }

    public boolean runWeekly() {
        return AlarmPeriod.WEEKLY.equals(this.period);
    }

    enum AlarmStatus {
        ACTIVE,
        INACTIVE
    }

    enum AlarmPeriod {
        ONCE,
        DAILY,
        WEEKLY
    }
}
