
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import domain.Finder;
import domain.Lessor;
import domain.Property;
import domain.Tenant;

@Service
@Transactional
public class FinderService {

	// Managed Repository --------------------------------------
	@Autowired
	private FinderRepository	finderRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	// Simple CRUD methods --------------------------------------
	public Finder create() {
		Finder result;
		Date oneHourAgo;

		oneHourAgo = new Date(System.currentTimeMillis() - 3600000);

		result = new Finder();
		result.setDestination("Spain");
		result.setCacheMoment(oneHourAgo);
		result.setResults(new ArrayList<Property>());
		result.setKeyword("");

		return result;
	}
	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(int finderId) {
		Finder result;

		result = finderRepository.findOne(finderId);

		return result;
	}

	public Finder save(Finder finder) {
		Finder result, old;
		Double min;
		Collection<Property> results;
		Date oneHourAgo, lastSearch;

		oneHourAgo = new Date(System.currentTimeMillis() - 3600000);
		lastSearch = new Date(finder.getCacheMoment().getTime());

		Assert.isTrue(((Tenant) actorService.findByPrincipal()).getFinder().getId() == finder.getId());

		if (finder.getMaxPrice() != null && finder.getMinPrice() != null) {
			Assert.isTrue(finder.getMaxPrice() >= finder.getMinPrice());
		}

		old = finderRepository.findOne(finder.getId());

		result = finder;

		if (lastSearch.before(oneHourAgo)
			|| !(finder.getDestination().equals(old.getDestination()) && finder.getKeyword().equals(old.getKeyword()) && (finder.getMaxPrice() == old.getMaxPrice() || (finder.getMaxPrice() != null && finder.getMaxPrice().equals(old.getMaxPrice()))) && (finder
				.getMinPrice() == old.getMinPrice() || (finder.getMinPrice() != null && finder.getMinPrice().equals(old.getMinPrice()))))) {
			result.setCacheMoment(new Date(System.currentTimeMillis() - 100));

			if (finder.getMinPrice() == null) {
				min = 0.;
			} else {
				min = finder.getMinPrice();
			}

			if (result.getMaxPrice() == null) {
				results = finderRepository.searchPropertiesWithoutMaxPrice(result.getDestination(), result.getKeyword(), min);
			} else {
				results = finderRepository.searchPropertiesWithMaxPrice(result.getDestination(), result.getKeyword(), min, result.getMaxPrice());
			}

			result.setResults(results);

			result = finderRepository.save(result);
		}

		Assert.notNull(result);

		return result;
	}

	public void delete(Finder finder) {
		Assert.notNull(finder, "finder.error.null");

		Assert.isTrue(finderRepository.exists(finder.getId()), "finder.error.exists");

		finderRepository.delete(finder);
	}

	// Other business methods --------------------------------------

	public Finder findByPrincipal() {
		Finder result;

		result = finderRepository.findByTenant(actorService.findByPrincipal().getId());

		return result;
	}

	public Double getAverageResultsPerFinder() {
		//Dashboard-11
		return finderRepository.getAverageResultsPerFinder();
	}

	public Integer getMinimumResultsPerFinder() {
		//Dashboard-11
		return finderRepository.getMinimumResultsPerFinder();
	}

	public Integer getMaximumResultsPerFinder() {
		//Dashboard-11
		return finderRepository.getMaximumResultsPerFinder();
	}

	public void removeLessorProperties(Lessor lessor) {
		Collection<Finder> finders;

		finders = findAll();

		for (Finder finder : finders) {
			finder.getResults().removeAll(lessor.getLessorProperties());
			finderRepository.save(finder);
		}
	}

	public void removeProperty(Property property) {
		Collection<Finder> finders;

		finders = finderRepository.findFindersFromProperty(property.getId());

		for (Finder finder : finders) {
			finder.getResults().remove(property);
			finderRepository.save(finder);
		}
	}
	public Finder reconstruct(Finder finder, BindingResult binding) {
		Finder res, old;

		old = findOne(finder.getId());

		res = create();

		res.setCacheMoment(old.getCacheMoment());
		res.setId(old.getId());
		res.setResults(old.getResults());
		res.setVersion(old.getVersion());
		res.setMinPrice(finder.getMinPrice());
		res.setMaxPrice(finder.getMaxPrice());
		res.setKeyword(finder.getKeyword());
		res.setDestination(finder.getDestination());

		validator.validate(res, binding);

		return res;
	}
}
