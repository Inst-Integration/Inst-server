// presentation/teacher/dto/response/TeacherResponse.java
package io.github.hll2071.inst.presentation.teacher.dto.response;

import io.github.hll2071.inst.domain.teacher.Teacher;
import io.github.hll2071.inst.shared.enums.Instrument;

public record TeacherResponse(
        Long id,
        String name,
        String bio,
        String profileImageUrl,
        Instrument instrument,
        int experienceYears,
        double rating
) {
    public static TeacherResponse from(Teacher teacher) {
        return new TeacherResponse(
                teacher.getId(),
                teacher.getName(),
                teacher.getBio(),
                teacher.getProfileImageUrl(),
                teacher.getInstrument(),
                teacher.getExperienceYears(),
                teacher.getRating()
        );
    }
}