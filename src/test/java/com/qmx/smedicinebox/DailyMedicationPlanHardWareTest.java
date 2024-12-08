package com.qmx.smedicinebox;

import com.qmx.smedicinebox.dto.DailyMedicationPlanHardWareDto;
import com.qmx.smedicinebox.sys.service.DailyMedicationPlanService;
import com.qmx.smedicinebox.utils.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DailyMedicationPlanHardWareTest {
    @Autowired
    private DailyMedicationPlanService dailyMedicationPlanService;
    @Test
    public void test() {
        R result = dailyMedicationPlanService.returnDailyMedicationPlan("1");

        System.out.println(result);
    }
}
