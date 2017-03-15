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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AttachmentService;
import services.MessageService;
import controllers.AbstractController;
import domain.Attachment;
import domain.Message;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageService		messageService;

	@Autowired
	private AttachmentService	attachmentService;


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

		return result;
	}

	@RequestMapping(value = "/sent", method = RequestMethod.GET)
	public ModelAndView sent() {
		ModelAndView result;
		Collection<Message> res;

		res = this.messageService.findSentMessageOfPrincipal();

		result = new ModelAndView("message/list");
		result.addObject("messages", res);

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

		return result;
	}
}
