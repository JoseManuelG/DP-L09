
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import repositories.CommentableRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Comment;
import domain.Customer;
import domain.Lessor;
import domain.Tenant;

@Service
@Transactional
public class CommentService {

	//Managed Repository-----------------------------

	@Autowired
	private CommentRepository	commentRepository;

	//Supporting services-----------------------------

	@Autowired
	private LoginService		loginService;
	@Autowired
	private CommentableRepository commentableRepository;
	
	@Autowired
	private TenantService		tenantService;
	
	@Autowired
	private LessorService		lessorService;


	//Constructors------------------------------------

	public CommentService() {
		super();
	}

	//Simple CRUD methods----------------------------

	public Comment create() {
		Comment result;

		result = new Comment();

		return result;
	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = commentRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Comment findOne(int commentId) {
		Comment result;

		result = commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;
	}

	@SuppressWarnings("static-access")
	public Comment save(Comment comment) {
		Assert.notNull(comment, "El comentario no puede ser nulo");
		Assert.isTrue(comment.getId() == 0, "No se pueden modificar comentarios");
		Assert.hasText(comment.getTitle(), "El titulo no puede ser nulo ni estar vacío");
		Assert.hasText(comment.getText(), "El texto no puede ser nulo ni estar vacío");
		Assert.isTrue(comment.getStars() >= 0, "No se puede dejar un comentario sin estrellas");
		Assert.notNull(comment.getPostMoment(), "La fecha de creación no puede ser nula");
		Assert.notNull(comment.getRecipient(), "El Recipient no puede ser nulo");
		Assert.isTrue(comment.getSender().getUserAccount().equals(loginService.getPrincipal()), "Solo el propietario puede realizar operaciones");
		Assert.isTrue(validComment(comment), "No tienes los derechos para comentar aqui");
		Comment result;

		result = commentRepository.save(comment);

		return result;
	}

	
	//Other bussiness methods------------------------
	
	public Collection<Comment> findAllCommentsOfACustomer(Customer customer) {
		Collection<Comment> comments;
		commentableRepository.findAll();
		comments = commentRepository.findCommentsByCustomerID(customer.getId());
		return comments;
	}
	
	public boolean lessorValidToBeCommentedByTenant(Lessor lessor, Tenant tenant){
		boolean result= false;
		if(lessorService.lessorHaveBooksWithTenant(tenant, lessor)){
			result=true;
		}
		return result;
	}
	
	public boolean tenantValidToBeCommentedByLessor(Lessor lessor, Tenant tenant){
		boolean result= false;
		if(tenantService.tenantHaveBooksWithLessor(tenant, lessor)){
			result=true;
		}
		return result;
	}
	
	public boolean validAutoComment(Comment comment){
		boolean result= false;
		if(comment.getSender().equals(comment.getRecipient())){
			result=true;
		}
		return result;
		
	}
	
	public boolean validComment(Comment comment){
		boolean result=false;
		//si se comenta a si mismo se autoValida
		if(validAutoComment(comment)){
			result=true;
		}else{
			
			Actor sender= comment.getSender();
			Actor recipient = (Actor) comment.getRecipient();
			
			ArrayList<Authority> authoritySender =new ArrayList<Authority>();
			ArrayList<Authority> authorityRecipient =new ArrayList<Authority>();
			
			
			authoritySender.addAll(sender.getUserAccount().getAuthorities());		
			authorityRecipient.addAll(recipient.getUserAccount().getAuthorities());		
			
			switch(authoritySender.get(0).getAuthority()){
				case (Authority.LESSOR):
					switch(authorityRecipient.get(0).getAuthority()){
					case (Authority.TENANT):
						result=tenantValidToBeCommentedByLessor((Lessor)sender,(Tenant)recipient);
						break;
					}
				break;
				case (Authority.TENANT):
					switch(authorityRecipient.get(0).getAuthority()){
					case (Authority.LESSOR):
						result=lessorValidToBeCommentedByTenant((Lessor)recipient,(Tenant)sender);
						break;
					}
				break;
					
			
			}
			
		}
		
		return result;
		
	}

}
