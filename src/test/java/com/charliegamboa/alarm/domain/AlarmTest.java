package com.charliegamboa.alarm.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.charliegamboa.alarm.domain.Alarm.AlarmPeriod.DAILY;
import static com.charliegamboa.alarm.domain.Alarm.AlarmPeriod.WEEKLY;
import static com.charliegamboa.alarm.domain.TestUtil.CHARLIE_GAMBOA;
import static com.charliegamboa.alarm.domain.TestUtil.WAKE_UP;

public class AlarmTest {


    private Alarm alarm = Alarm.setNew(LocalDateTime.of(2023, 1, 13, 12, 0, 0), User.create("Charlie Gamboa"), WAKE_UP);

    @Test
    void canCreateAnAlarm() {
        Assertions.assertThat(alarm).isNotNull();
        Assertions.assertThat(alarm.getDescription()).isNotNull();
        Assertions.assertThat(alarm.getDateTime()).isNotNull();
        Assertions.assertThat(alarm.getId()).isZero();
    }

    @Test
    void byDefaultAnAlarmIsActive(){
        Assertions.assertThat(alarm.isActive()).isTrue();
    }

    @Test
    void byDefaultAlarmRunOnce(){
        Assertions.assertThat(alarm.runOnce()).isTrue();
        Assertions.assertThat(alarm.runWeekly()).isFalse();
    }

    @Test
    void anAlarmCanBeInactivated(){
        Alarm alarm = newAlarm();
        alarm.inactivate();
        Assertions.assertThat(alarm.isActive()).isFalse();
    }

    private static Alarm newAlarm() {
        Alarm alarm = Alarm.setNew(LocalDateTime.of(2023, 1, 13, 12, 0, 0), User.create("Charlie Gamboa"), WAKE_UP);
        return alarm;
    }

    @Test
    void alarmOwnerCanNotBeNull() {
        Assertions.assertThatThrownBy(() -> {
                    Alarm.setNew(LocalDateTime.now(), null, WAKE_UP);
                }).hasMessage("Alarm's owner can not be null")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void alarmDescriptionCanNotBeNull() {
        Assertions.assertThatThrownBy(() -> {
                    Alarm.setNew(LocalDateTime.now(), User.create("Charlie Gamboa"), null);
                }).hasMessage("Alarm's description can not be null")
                .isInstanceOf(RuntimeException.class);
    }
    @Test
    void newAlarmMustHaveADateAndTime(){
        Assertions.assertThatThrownBy(() -> {
                    Alarm.setNew(null, User.create(CHARLIE_GAMBOA), WAKE_UP);
                }).hasMessage("Alarm's date and time can not be null")
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void repeatAlarmDaily(){
        Alarm alarm = newAlarm();
        alarm.repeat(DAILY);
        Assertions.assertThat(alarm.isDaily()).isTrue();
    }
    @Test
    void repeatAlarmWeekly(){
        Alarm alarm = newAlarm();
        alarm.repeat(WEEKLY);
        Assertions.assertThat(alarm.runWeekly()).isTrue();
        Assertions.assertThat(alarm.runOnce()).isFalse();
    }
}
