package github.com.miralhas.lecturizebackend.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class EnumPatternValidator implements ConstraintValidator<EnumPattern, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(EnumPattern constraint) {
        acceptedValues = Stream.of(constraint.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) return true;
        boolean isValid = acceptedValues.contains(value.toString());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Valor Inválido. Valores aceitos são: %s"
                    .formatted(acceptedValues)).addConstraintViolation();
        }
        return isValid;
    }
}
