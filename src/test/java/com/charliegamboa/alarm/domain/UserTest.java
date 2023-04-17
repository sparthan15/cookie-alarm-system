package com.charliegamboa.alarm.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.charliegamboa.alarm.domain.TestUtil.CHARLIE_GAMBOA;

public class UserTest {

    private User newUser = User.create(CHARLIE_GAMBOA);

    @Test
    void canCreateUser() {
        Assertions.assertThat(newUser).isNotNull();
    }

    @Test
    void newUserHasAnEmptyAlarmList() {
        Assertions.assertThat(newUser.getAlarms()).isEmpty();
    }

    @Test
    void userHasAName() {
        Assertions.assertThat(newUser.getName()).isEqualTo(CHARLIE_GAMBOA);
    }

    @Test
    void iCanAddANewAlarm() {
        User user = User.create(CHARLIE_GAMBOA);
        user.setNewAlarm(LocalDateTime.now(), "Wake up");
        Assertions.assertThat(user.getAlarms()).isNotEmpty();
    }

    @Test
    void iCanFetchAllOwnersAlarm() {
        Assertions.assertThat(newUser.getAlarms()).isEmpty();
    }

    @Test
    void aNewAlarmToAddCanNotBeNull() {
        Assertions.assertThatThrownBy(() -> {
            User user = User.create(CHARLIE_GAMBOA);
            user.setNewAlarm(null, "Wake up");
        });
    }

    @Test
    void inactivateAlarmsMassively() {
        User user = User.create(CHARLIE_GAMBOA);
        Alarm wakeUp = user.setNewAlarm(LocalDateTime.now(), "Wake up");
        Alarm pickGirlsUp = user.setNewAlarm(LocalDateTime.now(), "Wake up");
        Assertions.assertThat(user.getAlarms()).size().isEqualTo(2);
        user.inactivate(wakeUp, pickGirlsUp);
        Assertions.assertThat(user.getAlarms()).isEmpty();
    }
}
