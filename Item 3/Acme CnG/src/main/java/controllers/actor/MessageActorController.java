/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AttachmentService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Attachment;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageService		messageService;

	@Autowired
	private AttachmentService	attachmentService;

	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public MessageActorController() {
		super();
	}

	// Methods -----------------------------------------------------------------		

	@RequestMapping(value = "/received", method = RequestMethod.GET)
	public ModelAndView received() {
		ModelAndView result;
		Collection<Message> res;

		res = this.messageService.findReceivedMessageOfPrincipal();

		result = new ModelAndView("message/list");
		result.addObject("messages", res);
		result.addObject("requestURI", "message/actor/received.do");

		return result;
	}

	@RequestMapping(value = "/sent", method = RequestMethod.GET)
	public ModelAndView sent() {
		ModelAndView result;
		Collection<Message> res;

		res = this.messageService.findSentMessageOfPrincipal();

		result = new ModelAndView("message/list");
		result.addObject("messages", res);
		result.addObject("requestURI", "message/actor/sent.do");

		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int messageId) {
		ModelAndView result;
		Message res;
		Collection<Attachment> att;

		res = this.messageService.findOne(messageId);
		att = this.attachmentService.findAttachmentsOfMessage(res);

		result = new ModelAndView("message/view");
		result.addObject("res", res);
		result.addObject("attachments", att);
		result.addObject("requestURI", "message/actor/view.do?messageId=" + messageId);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView write() {
		ModelAndView result;
		MessageForm messageForm;

		messageForm = new MessageForm();
		result = this.createEditModelAndView(messageForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET, params = "addAttachment")
	public ModelAndView addAttachment(final MessageForm messageForm) {
		ModelAndView result;

		messageForm.addAttachmentSpace();

		result = this.createEditModelAndView(messageForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET, params = "removeAttachment")
	public ModelAndView removeAttachment(final MessageForm messageForm) {
		ModelAndView result;

		messageForm.removeAttachmentSpace();

		result = this.createEditModelAndView(messageForm);

		return result;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST, params = "save")
	public ModelAndView write(final MessageForm messageForm, final BindingResult bindingResult) {
		ModelAndView result;
		Message message;

		message = this.messageService.reconstruct(messageForm, bindingResult);
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else
			try {
				this.messageService.save(message, messageForm.getAttachments());
				result = new ModelAndView("redirect:sent.do");
			} catch (final IllegalArgumentException e) {
				result = this.createEditModelAndView(messageForm, e.getMessage());
			}

		return result;
	}

	///////////////////////////////////////////

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		ModelAndView result;

		result = this.createEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		ModelAndView result;
		Collection<Actor> actors;

		result = new ModelAndView("message/write");
		actors = this.actorService.findAll();

		result.addObject("actors", actors);
		result.addObject("messageForm", messageForm);
		result.addObject("message", message);
		result.addObject("requestURI", "message/actor/write.do");

		return result;
	}
}
