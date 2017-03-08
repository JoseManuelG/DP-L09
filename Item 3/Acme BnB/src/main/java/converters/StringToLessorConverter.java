package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LessorRepository;
import domain.Lessor;

@Component
@Transactional
public class StringToLessorConverter implements Converter<String, Lessor> {

	@Autowired
	LessorRepository lessorRepository;

	@Override
	public Lessor convert(String text) {
		Lessor result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = lessorRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
