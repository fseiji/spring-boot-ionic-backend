package com.fseiji.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.fseiji.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage simpleMailMessage);

}
