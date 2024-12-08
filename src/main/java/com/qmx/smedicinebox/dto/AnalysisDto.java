package com.qmx.smedicinebox.dto;

import com.qmx.smedicinebox.sys.entity.DailyMedicationPlanEntity;
import com.qmx.smedicinebox.sys.entity.MedicationSituationEntity;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class AnalysisDto {
    public AnalysisDto(Map<String, List<DailyMedicationPlanEntity>> dmpMap, Map<String, List<MedicationSituationEntity>> msMap, HashMap<String, Map<Integer, String>> integerStringArrayListMap) {
        this.dmpMap = dmpMap;
        this.msMap = msMap;
        this.integerStringArrayListMap = integerStringArrayListMap;
    }

    Map<String, List<DailyMedicationPlanEntity>> dmpMap;
    Map<String, List<MedicationSituationEntity>> msMap;
    HashMap<String, Map<Integer, String>> integerStringArrayListMap;

    @Override
    public String toString() {
        return "AnalysisDto{" +
                ", integerStringArrayListMap=" + integerStringArrayListMap +
                "dmpMap=" + dmpMap +
                ", msMap=" + msMap +
                '}';
    }
}
