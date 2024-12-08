package com.qmx.smedicinebox;

import com.qmx.smedicinebox.sys.scheduled.MessageEntityScheduledTasks;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DailyTasks {
    @Test
    public void test() {
        System.out.println(1);
        MessageEntityScheduledTasks messageEntityScheduledTasks = new MessageEntityScheduledTasks();

        messageEntityScheduledTasks.executeWeeklyTask();
    }
}
