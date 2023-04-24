package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeZone {
    public static void setTimeZone(Task task, User user) {
        ZoneId defTz = java.util.TimeZone.getDefault().toZoneId();
        ZoneId userTimeZone = ZoneId.of(user.getTimezone());
        LocalDateTime dateTime = task.getCreated()
                .atZone(defTz)
                .withZoneSameInstant(userTimeZone)
                .toLocalDateTime();
        task.setCreated(dateTime);
    }
}
