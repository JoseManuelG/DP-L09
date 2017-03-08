package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.AttributeValue;

@Component
@Transactional
public class AttributeValueToStringConverter implements Converter<AttributeValue, String> {

	@Override
	public String convert(AttributeValue attributeValue) {
		String result;

		if (attributeValue == null)
			result = null;
		else
			result = String.valueOf(attributeValue.getId());
		
		return result;
	}

}
