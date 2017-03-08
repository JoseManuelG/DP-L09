package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentableRepository;
import domain.Commentable;

@Component
@Transactional
public class StringToCommentableConverter implements Converter<String, Commentable> {

	@Autowired
	CommentableRepository commentableRepository;

	@Override
	public Commentable convert(String text) {
		Commentable result;
		int id;

		try {
			id = Integer.valueOf(text);
			
			commentableRepository.findAll();
			
			result = commentableRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
