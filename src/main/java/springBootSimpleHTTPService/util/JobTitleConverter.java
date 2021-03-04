package springBootSimpleHTTPService.util;

import springBootSimpleHTTPService.entity.JobEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * Clase de la capa de utilidades.
 *
 * Se utiliza tanto para mapear a base de datos (h2) el tipo Enum, definido como 'JobEnum'
 * y que contiene los empleos disponibles en el sistema (tipo String) y el salario que se
 * corresponde a cada empleo (tipo BigDecimal), como para mapear el valor String de la columna
 * que contiene el nombre del empleo (en la tabla 'role', columna 'jobtitle'), a la entidad JobEnum
 *
 * La anotacion @Converter especifica que la clase es un convertidor y define su alcance.
 * Una clase de convertidor define el mapeo relacional/objeto de tal forma que el proveedor
 * de persistencia debe aplicar automaticamente el convertidor a todos los atributos mapeados
 * del tipo de destino especificado para todas las entidades de la unidad de persistencia
 */
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
