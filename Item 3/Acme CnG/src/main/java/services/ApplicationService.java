package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;

@Service
@Transactional
public class ApplicationService {
	
	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;
	
	//Supported Services--------------------------------------------------------------------

	@Autowired
	private TripService tripService;
	
	@Autowired
	private CustomerService customerService;
	
	
	//Simple CRUD methods-------------------------------------------------------------------
	public Application create(int tripId){
		Application result = new Application();
		result.setTrip(tripService.findOne(tripId));
		result.setCustomer(customerService.findCustomerByPrincipal());
		result.setStatus("PENDING");
		return result;
	}
	
	
	public Collection<Application> findAll(){
		return applicationRepository.findAll();
	}
	
	public Application findOne(Integer applicationId){
		
		Assert.isNull(applicationId, "No puedes encontrar una solicitud sin ID");
		Assert.isTrue(applicationId<=0,"La Id no es válida");
		
		
		Application result = applicationRepository.findOne(applicationId);
		
		return result;
	}
	
	
	
	public Application save(Application application){
		Application result;
		
		Assert.notNull(application,"La soicitud no puede ser nula");
		Assert.isTrue(application.getStatus().equals("PENDING")
						||application.getStatus().equals("ACCEPTED") 
						|| application.getStatus().equals("DENIED")
						,"La solicitud debe tener un estado válido");
	
		result = applicationRepository.save(application);
		return result;
	}
	
	public void delete(Application application) {
		Assert.notNull(application, "La solicitud no puede ser nula");
		Assert.isTrue(applicationRepository.exists(application.getId()), "La solicitud debe estar en la base de datos antes de borrarla");

		applicationRepository.delete(application);
	}
	
	//Other Business methods-------------------------------------------------------------------
	
	public Application acceptApply(int applyId){
		Application aux,result;
		
		aux = applicationRepository.findOne(applyId);
		
		aux.setStatus("ACCEPTED");
		result = this.save(aux);
		return result;
		
	}
	
	public Application denyApply(int applyId){
		Application aux,result;
		
		aux = applicationRepository.findOne(applyId);
		
		aux.setStatus("DENIED");
		result = this.save(aux);
		return result;
		
	}
	
	public Collection<Application> findAllApplicationsByTrip(int tripId){
		Collection<Application> result;
		result = applicationRepository.findAllApplicationsByTripId(tripId);
		return result;
	}
	
	

}
