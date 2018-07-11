package com.fseiji.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fseiji.cursomc.domain.Cliente;
import com.fseiji.cursomc.dto.ClienteDTO;
import com.fseiji.cursomc.dto.ClienteNewDTO;
import com.fseiji.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente cliente = clienteService.find(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
		Cliente cliente = clienteService.fromCliente(clienteNewDTO);
		cliente = clienteService.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO categoriaDTO, @PathVariable Integer id) {
		Cliente categoria = clienteService.fromCliente(categoriaDTO);
		categoria.setId(id);
		categoria = clienteService.update(categoria);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = clienteService.findAll();
		List<ClienteDTO> listDto = list.stream().map(categoriaDto -> new ClienteDTO(categoriaDto))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> listPage = clienteService.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = listPage.map(categoriaDto -> new ClienteDTO(categoriaDto));
		return ResponseEntity.ok().body(listDto);
	}

}
