package com.fseiji.cursomc.resources.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.fseiji.cursomc.domain.Cliente;
import com.fseiji.cursomc.dto.ClienteDTO;
import com.fseiji.cursomc.repositories.ClienteRepository;
import com.fseiji.cursomc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) httpServletRequest
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		if (cliente != null && !cliente.getId().equals(uriId)) {
			list.add(new FieldMessage("Email", "Email já existente"));
		}

		// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}