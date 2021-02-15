package springBootSimpleHTTPService.util;

import springBootSimpleHTTPService.entity.JobEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class JobTitleConverter implements AttributeConverter<JobEnum, String> {
    @Override
    public String convertToDatabaseColumn(JobEnum enumAttribute) {
        if (enumAttribute == null) {
            return null;
        }
        return enumAttribute.getJobTitle();
    }

    @Override
    public JobEnum convertToEntityAttribute(String jobTitle) {
        if (jobTitle == null) {
            return null;
        }

        return Stream.of(JobEnum.values())
                .filter(j -> j.getJobTitle().equals(jobTitle))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
