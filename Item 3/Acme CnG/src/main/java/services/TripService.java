package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TripRepository;
import domain.Trip;

@Service
@Transactional
public class TripService {
	
			//Managed Repository--------------------------------------------------------------------
			@Autowired
			private TripRepository	tripRepository;
			
			//Supported Services--------------------------------------------------------------------
			
			@Autowired
			private CustomerService customerService;
			
			@Autowired
			private Validator validator;
			
			
			//Simple CRUD methods-------------------------------------------------------------------
			public Trip create(){
				Trip result = new Trip();
				result.setBanned(false);
				result.setCustomer(customerService.findCustomerByPrincipal());
				
				return result;
			}
			
			public Trip create(String type){
				Trip result = new Trip();
				result.setType(type);
				result.setBanned(false);
				result.setCustomer(customerService.findCustomerByPrincipal());
				return result;
			}
			
			
			public Collection<Trip> findAll(){
				return tripRepository.findAll();
			}
			
			public Trip findOne(Integer tripId){
				
				Assert.isNull(tripId, "No Puedes Encontrar un viaje sin ID");
				Assert.isTrue(tripId<=0,"La Id no es valida");
				
				
				Trip result = tripRepository.findOne(tripId);
				
				return result;
			}
			
			
			
			public Trip save(Trip trip){
				Trip result;
				
				Assert.notNull(trip,"El viaje no puede ser nulo");
				Assert.hasText(trip.getTitle(),"El viaje debe tener un título");
				Assert.hasText(trip.getDescription(),"El viaje debe tener una descripción");
				Assert.notNull(trip.getDepartureTime(),"El viaje debe tener un momento de salida");
				Assert.hasText(trip.getOrigin(),"El viaje debe tener un lugar de salida");
				Assert.hasText(trip.getDestination(),"El viaje debe tener un lugar de llegada");
				Assert.isTrue((trip.getOriginLat()==null && trip.getOriginLon()==null) || ((!(trip.getOriginLat()==null) && !(trip.getOriginLon()==null))), "Si se definen las coordenadas del lugar de salida, se deben definir ambas");
				Assert.isTrue((trip.getDestinationLat()==null && trip.getDestinationLon()==null) || ((!(trip.getDestinationLat()==null) && !(trip.getDestinationLon()==null))), "Si se definen las coordenadas del lugar de llegada, se deben definir ambas");
				Assert.notNull(trip.getType(),"El viaje debe ser de algún tipo");
			
				result = tripRepository.save(trip);
				return result;
			}
			
			public void delete(Trip trip) {
				Assert.notNull(trip, "El viaje no puede ser nulo");
				Assert.isTrue(tripRepository.exists(trip.getId()), "El viaje debe estar en base de datos antes de borrarlo");

				tripRepository.delete(trip);
			}
			
			//Other Business methods-------------------------------------------------------------------
			
			public Trip reconstruct(Trip trip, BindingResult bindingResult) {
				Trip result, original;

				if (trip.getId() == 0) {
					result = this.create();
				} else {
					original = tripRepository.findOne(trip.getId());
					result = new Trip();
					result.setBanned(original.getBanned());
				}
				result.setCustomer(trip.getCustomer());
				result.setDepartureTime(trip.getDepartureTime());
				result.setDescription(trip.getDescription());
				result.setDestination(trip.getDestination());
				result.setDestinationLat(trip.getDestinationLat());
				result.setDestinationLon(trip.getDestinationLon());
				result.setOrigin(trip.getOrigin());
				result.setOriginLat(trip.getOriginLat());
				result.setOriginLon(trip.getOriginLat());
				result.setTitle(trip.getTitle());
				result.setType(trip.getTitle());
				validator.validate(result, bindingResult);
				return result;

			}
			
			public Trip createOffer(){
				Trip result;

				String type="OFFER";
				
				result =this.create(type);
				return result;
			}
			
			public Trip createRequest(){
				Trip result;

				String type="REQUEST";
				
				result =this.create(type);
				return result;
			}
			
			public Collection<Trip> findByKeyWord(String keyword){
				Collection<Trip> result;
				
				result=tripRepository.findByKeyWord(keyword);
				
				return result;
				
			}
			
			public void banTrip(int tripId){
				Trip aux = tripRepository.findOne(tripId);
				aux.setBanned(true);
				this.save(aux);
			}
			
			public Collection<Trip> findAllOffers(){
				Collection<Trip> result;
				result = tripRepository.findAllOffers();
				return result;
			}
			
			public Collection<Trip> findAllRequests(){
				Collection<Trip> result;
				result = tripRepository.findAllRequests();
				return result;
			}


}
