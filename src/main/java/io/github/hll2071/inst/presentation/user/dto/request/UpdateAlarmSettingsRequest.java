package io.github.hll2071.inst.presentation.user.dto.request;

public record UpdateAlarmSettingsRequest(
        boolean practiceAlarm,
        boolean eventAlarm,
        boolean contentAlarm
) {}